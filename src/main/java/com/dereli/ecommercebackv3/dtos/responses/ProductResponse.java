package com.dereli.ecommercebackv3.dtos.responses;

import lombok.Data;

import java.util.Set;

@Data
public class ProductResponse {

    String code;

    String name;

    String description;

    String picture;

    double avgRating;

    Set<String> galleryImages;

    Integer stockValue;

    Boolean isApproved;

    Double price;

    String categoryCode;

    String categoryName;

    Set<String> keywords;
}
