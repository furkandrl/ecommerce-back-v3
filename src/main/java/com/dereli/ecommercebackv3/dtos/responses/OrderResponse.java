package com.dereli.ecommercebackv3.dtos.responses;

import lombok.Data;

import java.util.Set;

@Data
public class OrderResponse {

    String pk;

    Set<EntryResponse> entries;

    Double totalPriceOfProducts;

    Double totalPrice;

    String orderStatus;

    String shippingTrackingLink;

    String createdAt;

}
