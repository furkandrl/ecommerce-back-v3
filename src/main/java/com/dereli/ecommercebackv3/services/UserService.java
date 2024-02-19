package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.requests.CustomerRequest;
import com.dereli.ecommercebackv3.dtos.responses.CustomerResponse;

public interface UserService {

    CustomerResponse getProfile();
    void updateProfile(CustomerRequest customerRequest);

}
