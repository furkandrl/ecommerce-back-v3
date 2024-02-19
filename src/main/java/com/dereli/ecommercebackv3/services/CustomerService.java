package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.requests.CustomerRequest;
import com.dereli.ecommercebackv3.dtos.responses.AddressResponse;
import com.dereli.ecommercebackv3.models.Customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerByUsername(String username) throws Exception;

    Boolean isCustomerExists(String username);

    Customer getCurrentCustomer() throws Exception;

    Customer register(Customer customer);

    void updateCustomer(CustomerRequest customerDto) throws Exception;

    void deleteCustomerByUid(String uid) throws Exception;

    List<AddressResponse> getAddressesForCustomer() throws Exception;

}
