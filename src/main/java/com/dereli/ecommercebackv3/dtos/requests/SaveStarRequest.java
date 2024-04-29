package com.dereli.ecommercebackv3.dtos.requests;

import lombok.Data;

@Data
public class SaveStarRequest {
    String productCode;
    double customerGivenStar;
}
