package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.constants.Exceptions;
import com.dereli.ecommercebackv3.daos.AddressDao;
import com.dereli.ecommercebackv3.dtos.requests.AddressRequest;
import com.dereli.ecommercebackv3.models.Address;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.services.AddressService;
import com.dereli.ecommercebackv3.services.SessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultAddressService implements AddressService {

    @Resource
    AddressDao addressDao;

    @Resource
    SessionService sessionService;

    @Override
    public Address findAddressByCode(String code) throws Exception {
        Optional<Address> optionalAddress = addressDao.getAddressByCodeAndCustomer(code, sessionService.getCurrentCustomer());
        if(optionalAddress.isPresent()) {
            return optionalAddress.get();
        } else {
            throw new Exception(Exceptions.ADDRESS_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public void editAddress(String code, AddressRequest addressRequest) throws Exception {
        Optional<Address> optionalAddress = addressDao.getAddressByCodeAndCustomer(code, sessionService.getCurrentCustomer());
        if(optionalAddress.isPresent()){
            Address address = optionalAddress.get();
            address.setAddress(addressRequest.getAddress());
        } else {
            throw new Exception(Exceptions.ADDRESS_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public void createAddress(AddressRequest addressRequest) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Optional<Address> optionalAddress = addressDao.getAddressByCodeAndCustomer(addressRequest.getCode(), customer);
        if (optionalAddress.isPresent()) {
            throw new Exception("You already have address with same code: " + addressRequest.getCode());
        }
        Address address = new Address();
        address.setCustomer(customer);
        if(Objects.nonNull(Objects.nonNull(addressRequest.getAddress()))) {
            address.setCode(addressRequest.getCode());
            address.setName(addressRequest.getName());
            address.setAddress((addressRequest.getAddress()));
        } else {
            throw new Exception("All fields must be filled.");
        } try {
            addressDao.save(address);
        } catch (Exception e) {
            throw new Exception("Error while saving address.");
        }
    }

    @Override
    public void deleteAddress(String addressCode) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Optional<Address> optionalAddress = addressDao.getAddressByCodeAndCustomer(addressCode, customer);
        if (optionalAddress.isPresent()) {
            addressDao.delete(optionalAddress.get());
        } else {
            throw new Exception("Address not found for code: " + addressCode);
        }
    }

}
