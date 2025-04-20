package com.bridgeLabz.addressApp.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto {
    private String message;
    private HttpStatus status;
    public ResponseDto(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }
}
