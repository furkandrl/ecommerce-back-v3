package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.daos.AddressDao;
import com.dereli.ecommercebackv3.daos.ModelDao;
import com.dereli.ecommercebackv3.models.Address;
import com.dereli.ecommercebackv3.utils.JwtTokenUtil;
import com.dereli.ecommercebackv3.dtos.requests.CustomerRequest;
import com.dereli.ecommercebackv3.constants.Exceptions;
import com.dereli.ecommercebackv3.daos.CustomerDao;
import com.dereli.ecommercebackv3.dtos.responses.AddressResponse;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCustomerService implements CustomerService {

    @Resource
    private CustomerDao customerDao;

    @Resource
    private ModelDao modelDao;

    @Resource
    private AddressDao addressDao;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    ModelMapper modelMapper;

    @Override
    public Customer findCustomerByUsername(String username) throws Exception {
        Optional<Customer> optionalCustomer = customerDao.getCustomerByUsername(username);
        if(optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new Exception(Exceptions.CUSTOMER_NOT_FOUND_USERNAME + username);
        }
    }

    public Boolean isCustomerExists(String username) {
        Optional<Customer> customerByUsername = customerDao.getCustomerByUsername(username);
        return customerByUsername.isPresent();
    }

    @Override
    public Customer getCurrentCustomer() throws Exception {
        return jwtTokenUtil.getUserFromToken();
    }


    @Override
    public Customer register(Customer customer) {
        modelDao.save(customer);
        return customer;
    }

    @Override
    public void updateCustomer(CustomerRequest customerDto) throws Exception {
//        Customer customer = getCustomerWithUid(customerDto.getUid());
        // todo : customer dto dan gelen veriler ile update edilecek.
//        modelDao.save(customer);
    }

    @Override
    public void deleteCustomerByUid(String uid) throws Exception {
        customerDao.delete(getCustomerWithUid(uid));
    }

    @Override
    public List<AddressResponse> getAddressesForCustomer() throws Exception {
        Customer customer = jwtTokenUtil.getUserFromToken();
        List<Address> addresses = addressDao.getAddressesByCustomer(customer);
        return Objects.nonNull(addresses) && addresses.size() > 0 ? addresses
                .stream()
                .map(address -> modelMapper.map(address, AddressResponse.class))
                .collect(Collectors.toList())
                : null;
    }


    private Customer getCustomerWithUid(String uid) throws Exception {
        Optional<Customer> customer = customerDao.getCustomerByUsername(uid);
        if(customer.isPresent()){
            return customer.get();
        } else {
            throw new Exception(Exceptions.CUSTOMER_NOT_FOUND_USERNAME + uid);
        }
    }
}
