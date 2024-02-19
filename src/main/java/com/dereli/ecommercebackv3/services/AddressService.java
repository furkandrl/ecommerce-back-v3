package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.requests.AddressRequest;
import com.dereli.ecommercebackv3.models.Address;

public interface AddressService {

    Address findAddressByCode(String code) throws Exception;

    void editAddress(String code, AddressRequest addressRequest) throws Exception;

    void createAddress(AddressRequest addressRequest) throws Exception;

    void deleteAddress(String addressCode) throws Exception;

}
