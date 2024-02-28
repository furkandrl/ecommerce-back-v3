package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.daos.AddressDao;
import com.dereli.ecommercebackv3.dtos.responses.AddressResponse;
import com.dereli.ecommercebackv3.dtos.responses.CartResponse;
import com.dereli.ecommercebackv3.dtos.responses.CheckoutResponse;
import com.dereli.ecommercebackv3.models.Address;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.services.CartService;
import com.dereli.ecommercebackv3.services.CheckoutService;
import com.dereli.ecommercebackv3.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCheckoutService implements CheckoutService {

    @Resource
    private SessionService sessionService;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private AddressDao addressDao;

    @Resource
    private CartService cartService;

    @Override
    public CheckoutResponse getCheckoutPage() {
        Customer customer = sessionService.getCurrentCustomer();
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setCart(modelMapper.map(cartService.getCartForCustomer(), CartResponse.class));
        List<Address> addresses = addressDao.getAddressesByCustomer(customer);
        if (addresses != null && addresses.size() > 0) {
            checkoutResponse.setAddresses(addresses.stream().map(address -> modelMapper.map(address, AddressResponse.class)).collect(Collectors.toList()));
        }
        return checkoutResponse;
    }

}
