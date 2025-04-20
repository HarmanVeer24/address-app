package com.bridgeLabz.addressApp.service;

import com.bridgeLabz.addressApp.dto.AddressDto;
import com.bridgeLabz.addressApp.dto.ResponseDto;
import com.bridgeLabz.addressApp.model.Address;
import com.bridgeLabz.addressApp.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddressBook {
    @Autowired
    private AddressRepository addressRepository;


    public ResponseDto addAddressData(AddressDto user){
        log.info("IN UserService");
        Address u=new Address();
        u.change(user);

        log.info("ACCESSING DATABASE");
        addressRepository.save(u);
        log.info("CREATED USER");
        return new ResponseDto("User created Successfully", HttpStatus.CREATED);
    }

    public ResponseDto getUser(Long id){

        Optional<Address> user=addressRepository.findById(id);

        if(user.isEmpty()){
            return new ResponseDto("User not found", HttpStatus.NOT_FOUND);
        }
        Address u=user.get();
        return new ResponseDto(
                u.getName() + " " +
                        u.getCity() + " " +
                        u.getPhoneNo() + " " +
                        u.getAddress() + " " +
                        u.getState() + " " +
                        u.getZipcode(),
                HttpStatus.OK
        );

    }


    public List<Address> getAllAddressData(){
        return addressRepository.findAll();
    }

    public ResponseDto deleteUser(Long id){
        addressRepository.deleteById(id);
        return new ResponseDto("User deleted successfully", HttpStatus.OK);
    }

    public ResponseDto updateUser(Long id,AddressDto user){
        log.info("Updating User");
        Optional<Address> u=addressRepository.findById(id);
        Address u1=u.get();
        u1.change(user);
        addressRepository.save(u1);
        return new ResponseDto("User modified Successfully", HttpStatus.OK);
    }
}
