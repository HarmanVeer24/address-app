package com.bridgeLabz.addressApp.service;


import com.bridgeLabz.addressApp.dto.*;
import com.bridgeLabz.addressApp.model.User;
import com.bridgeLabz.addressApp.repository.UserRepository;
import com.bridgeLabz.addressApp.utility.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    public boolean existsByEmail(String email) {
        log.debug("Checking existence of user by email: {}", email);
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseDto registerUser(RegisterDto registerDTO) {
        log.info("Registering user:{}", registerDTO.getEmail());
        ResponseDto res = new ResponseDto("", HttpStatus.OK);
        if (existsByEmail(registerDTO.getEmail())) {
            log.warn("registration failed: user already exists with email {}", registerDTO.getEmail());
            res.setMessage("error");
            return res;
        }
        User user = new User();
        user.setFullName(registerDTO.getFullname());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("user {} registered successfully", user.getEmail());
        res.setMessage("message");
        return res;

    }


    @Override
    public ResponseDto loginUser(LoginDto loginDTO) {
        log.info("Login attempt for user: {}", loginDTO.getEmail());
        ResponseDto res = new ResponseDto("",HttpStatus.OK);
        Optional<User> userExists = getUserByEmail(loginDTO.getEmail());

        if (userExists.isPresent()) {
            User user = userExists.get();
            if (matchPassword(loginDTO.getPassword(), user.getPassword())) {
                String token = jwtUtility.generateToken(user.getEmail());
                user.setToken(token);
                userRepository.save(user);

                log.debug("Login successful for user: {} - Token generated", user.getEmail());
                emailService.sendEmail(user.getEmail(), "Logged in Employee Payroll Application. You have been successfully logged in",token);
                log.error("User not found with email: {}", loginDTO.getEmail());
                res.setMessage("message " + token) ;
                return res;
            } else {
                log.warn("Invalid credentials for user: {}", loginDTO.getEmail());
                res.setMessage("error");
                return res;
            }
        }
        res.setMessage("error");
        return res;
    }
    @Override
    public void initiatePasswordReset(ForgotPasswordDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = generateOtp();
        System.out.println(otp);
        user.setOtp(otp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
        log.info(user.getOtp());
        emailService.sendOtpEmail(user.getEmail(),"The Otp for user "+user.getFullName()," is " + user.getOtp());
    }
    @Override
    public void verifyOtp(VerifyOtpDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOtp().equals(request.getOtp()) ||
                user.getOtpExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired OTP");
        }
    }
    @Override
    public void resetPassword(ResetPasswordDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOtp().equals(request.getOtp()) ||
                user.getOtpExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setOtp(null);
        user.setOtpExpiration(null);
        userRepository.save(user);
    }
    @Override
    public void ChangePassword(ChangePasswordDto changePasswordDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
         User user = userRepository.findByEmail(email)
                 .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(changePasswordDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid current password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
         userRepository.save(user);
    }
    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        log.debug("Matching password for login attempt");
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}










