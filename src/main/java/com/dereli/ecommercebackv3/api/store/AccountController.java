package com.dereli.ecommercebackv3.api.store;

import com.dereli.ecommercebackv3.constants.DereliCoreConstants;
import com.dereli.ecommercebackv3.dtos.requests.AddressRequest;
import com.dereli.ecommercebackv3.dtos.requests.CustomerRequest;
import com.dereli.ecommercebackv3.dtos.responses.AddressResponse;
import com.dereli.ecommercebackv3.dtos.responses.CustomerResponse;
import com.dereli.ecommercebackv3.services.AddressService;
import com.dereli.ecommercebackv3.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/my-account")
public class AccountController {

    @Resource
    private CustomerService customerService;

    @Resource
    AddressService addressService;

    @Resource
    ModelMapper modelMapper;

    @GetMapping("/profile")
    public ResponseEntity profile() {
        try {
            return new ResponseEntity<>(modelMapper.map(customerService.getCurrentCustomer(), CustomerResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit-profile")
    public ResponseEntity editProfile(@RequestBody CustomerRequest customerDto) {
        try {
            customerService.updateCustomer(customerDto);
            return new ResponseEntity<>(DereliCoreConstants.PROFILE_UPDATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/addresses")
    public ResponseEntity getAddresses() {
        try {
            return new ResponseEntity<>(customerService.getAddressesForCustomer(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-address")
    public ResponseEntity createAddress(@RequestBody AddressRequest addressRequest) {
        try {
            addressService.createAddress(addressRequest);
            return new ResponseEntity<>(DereliCoreConstants.ADDRESS_ADDED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/edit-address/{addressCode}")
    public ResponseEntity getAddressForCode(@PathVariable String addressCode) {
        try {
            return new ResponseEntity<>(modelMapper.map(addressService.findAddressByCode(addressCode), AddressResponse.class), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-address/{addressCode}")
    public ResponseEntity editAddress(@PathVariable String addressCode, @RequestBody AddressRequest addressRequest) {
        try {
            addressService.editAddress(addressCode, addressRequest);
            return new ResponseEntity<>(DereliCoreConstants.ADDRESS_UPDATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-address/{addressCode}")
    public ResponseEntity deleteAddress(@PathVariable String addressCode) {
        try {
            addressService.deleteAddress(addressCode);
            return new ResponseEntity<>(DereliCoreConstants.ADDRESS_DELETED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
