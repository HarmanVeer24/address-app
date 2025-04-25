package com.bridgeLabz.addressApp.controller;

import com.bridgeLabz.addressApp.dto.AddressDto;
import com.bridgeLabz.addressApp.dto.ResponseDto;
import com.bridgeLabz.addressApp.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/address-book")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressBook;


    @PostMapping("/create")
    public ResponseDto createUser(@Validated @RequestBody AddressDto user){
        log.info("Creating User");
        return addressBook.addAddressData(user);
    }

    @GetMapping("/user/{id}")
    public ResponseDto findUserById(@PathVariable(value="id") Long id){
        log.info("Finding User");
        return addressBook.getUser(id);
    }

    @GetMapping("/display")
    public List<?> displayUser(){
        log.info("Displaying User");
        return addressBook.getAllAddressData();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteUser(@PathVariable(value="id") Long id){
        log.info("Deleting User");
        return addressBook.deleteUser(id);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseDto updateUser(@PathVariable(value="id") Long id, @Validated @RequestBody AddressDto user){
        log.info("Updating User");
        return addressBook.updateUser(id,user);
    }

}