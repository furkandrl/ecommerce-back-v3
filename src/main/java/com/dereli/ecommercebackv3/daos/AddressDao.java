package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Address;
import com.dereli.ecommercebackv3.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressDao extends JpaRepository<Address, Long> {

    Optional<Address> getAddressByCodeAndCustomer(String code, Customer customer);

    List<Address> getAddressesByCustomer(Customer customer);

}
