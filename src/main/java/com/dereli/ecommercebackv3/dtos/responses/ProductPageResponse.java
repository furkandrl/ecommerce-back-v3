package com.dereli.ecommercebackv3.dtos.responses;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ProductPageResponse {

    CategoryResponse category;

    Page<ProductResponse> products;
}
