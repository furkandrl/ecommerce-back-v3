package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.dtos.requests.CustomerRequest;
import com.dereli.ecommercebackv3.dtos.responses.CustomerResponse;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.services.SessionService;
import com.dereli.ecommercebackv3.services.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.Resource;

public class DefaultUserService implements UserService {

    @Resource
    SessionService sessionService;

    @Resource
    ModelMapper modelMapper;

    @Override
    public CustomerResponse getProfile() {
        Customer currentCustomer = sessionService.getCurrentCustomer();
        return modelMapper.map(currentCustomer, CustomerResponse.class);
    }

    @Override
    public void updateProfile(CustomerRequest dto) {
        Customer customer = sessionService.getCurrentCustomer();
        if (dto.getUsername() != null) {
            customer.setUsername(dto.getUsername());
        }
        if (dto.getFirstName() != null) {
            customer.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            customer.setLastName(dto.getLastName());
        }
    }

}
