package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.responses.OrderListResponse;
import com.dereli.ecommercebackv3.models.Order;

public interface OrderService {

    public OrderListResponse getOrdersForCustomer();

    public Order findOrderDetailsForCode(String orderCode) throws Exception;

    public void placeOrder() throws Exception;

}
