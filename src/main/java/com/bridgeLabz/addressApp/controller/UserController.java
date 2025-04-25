package com.bridgeLabz.addressApp.controller;


import com.bridgeLabz.addressApp.dto.*;
import com.bridgeLabz.addressApp.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody RegisterDto registerDTO) {
        ResponseDto response = userService.registerUser(registerDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginDto loginDTO) {
        ResponseDto response = userService.loginUser(loginDTO);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto request) {
        userService.initiatePasswordReset(request);
        return ResponseEntity.ok("OTP sent to registered email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDto request) {
        userService.verifyOtp(request);
        return ResponseEntity.ok("OTP verified successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        userService.ChangePassword(changePasswordDto);
        return ResponseEntity.ok("Password changed successfully");
    }
}