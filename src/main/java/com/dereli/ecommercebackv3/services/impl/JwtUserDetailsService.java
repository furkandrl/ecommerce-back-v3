package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.dtos.requests.CustomerRequest;
import com.dereli.ecommercebackv3.dtos.responses.JwtResponse;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.services.CustomerService;
import com.dereli.ecommercebackv3.services.StoreService;
import com.dereli.ecommercebackv3.utils.JwtTokenUtil;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Resource
    private CustomerService customerService;

    @Resource
    private PasswordEncoder bcryptEncoder;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private StoreService storeService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = this.customerService.findCustomerByUsername(username);
        return new User(customer.getUsername(), customer.getPassword(),
                new ArrayList<>());
    }

    public JwtResponse register(CustomerRequest request) throws Exception {
        if (customerService.isCustomerExists(request.getUsername())) {
            throw new Exception("Bu mail adresi kullanılmaktadır.");
        }
        Customer customer = modelMapper.map(request, Customer.class);
        customer.setPassword(bcryptEncoder.encode(customer.getPassword()));
        customer.setFavProducts(Collections.emptySet());
        customer.setStore(storeService.getStoreModel());
        customerService.register(customer);

        UserDetails userDetails = userDetailsService.loadUserByUsername(customer.getUsername());

        return new JwtResponse(jwtTokenUtil.generateToken(userDetails), 0L);
    }

}