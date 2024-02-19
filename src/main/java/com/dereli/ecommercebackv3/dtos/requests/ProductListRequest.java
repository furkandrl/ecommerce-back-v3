package com.dereli.ecommercebackv3.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class ProductListRequest {

    List<ProductRequest> products;

}
