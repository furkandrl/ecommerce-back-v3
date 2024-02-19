package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Cart;
import com.dereli.ecommercebackv3.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDao extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByCustomer(Customer customer);

}
