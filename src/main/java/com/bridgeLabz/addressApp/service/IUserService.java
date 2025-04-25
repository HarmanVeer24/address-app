package com.bridgeLabz.addressApp.service;

import com.bridgeLabz.addressApp.dto.*;

public interface IUserService {
    public ResponseDto loginUser(LoginDto loginDTO);
    public ResponseDto registerUser(RegisterDto registerDTO);
    public void initiatePasswordReset(ForgotPasswordDto request);
    public void verifyOtp(VerifyOtpDto request);
    public void resetPassword(ResetPasswordDto request);
    public void ChangePassword(ChangePasswordDto changePasswordDto);
}
