package com.bridgeLabz.addressApp.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String name;
    private String email;
    private String address;
    private String city;
    private String state;
    private String phoneNo;
    private int zipcode;
}