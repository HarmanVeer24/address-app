package com.bridgeLabz.addressApp.model;

import com.bridgeLabz.addressApp.dto.AddressDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String name;
    private String phoneNo;
    private String address;
    private String city;
    private String state;
    private int zipcode;
    public Address(){}
    public void change(AddressDto user) {

        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setState(user.getState());
        this.setPhoneNo(user.getPhoneNo());
        this.setAddress(user.getAddress());
        this.setCity(user.getCity());
        this.setZipcode(user.getZipcode());
    }
}
