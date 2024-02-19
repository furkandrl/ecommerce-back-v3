package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends JpaRepository<Order, Long> {

    List<Order> getOrdersByCustomer(Customer customer);

    Optional<Order> getOrderByPk(String pk);

}
