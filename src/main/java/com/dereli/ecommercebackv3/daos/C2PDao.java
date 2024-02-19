package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.models.Customer2ProductRating;
import com.dereli.ecommercebackv3.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface C2PDao extends JpaRepository<Customer2ProductRating, Long> {

    Set<Customer2ProductRating> findCustomer2ProductRatingsByCustomer (Customer customer);

    Customer2ProductRating findCustomer2ProductRatingsByCustomerAndAndProduct (Customer customer, Product product);

}
