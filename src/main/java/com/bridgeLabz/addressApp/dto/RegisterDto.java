package com.bridgeLabz.addressApp.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String fullname;
    private String Password;

    public RegisterDto(String email, String fullname, String password) {
        this.email = email;
        this.fullname = fullname;
        this.Password = password;
    }
}
