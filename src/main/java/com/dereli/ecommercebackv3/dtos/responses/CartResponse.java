package com.dereli.ecommercebackv3.dtos.responses;

import lombok.Data;

import java.util.Set;

@Data
public class CartResponse {

    Set<EntryResponse> entries;

    Double totalPriceOfProducts;

    Integer numberOfProducts;


}
