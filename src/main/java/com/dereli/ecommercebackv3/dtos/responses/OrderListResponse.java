package com.dereli.ecommercebackv3.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class OrderListResponse {

    List<OrderResponse> orders;

}
