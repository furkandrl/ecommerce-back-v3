package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    Optional<Customer> getCustomerByUsername(String uid);

    Customer getCustomerByPk(Long pk);

}
