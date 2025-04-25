package com.bridgeLabz.addressApp.service;

import com.bridgeLabz.addressApp.dto.AddressDto;
import com.bridgeLabz.addressApp.dto.ResponseDto;
import com.bridgeLabz.addressApp.model.Address;
import java.util.List;

public interface IAAddressService {
    public ResponseDto addAddressData(AddressDto addressDto);
    public ResponseDto getUser(Long id);
    public List<Address> getAllAddressData();
    public ResponseDto deleteUser(Long id);
    public ResponseDto updateUser(Long id, AddressDto user);
}
