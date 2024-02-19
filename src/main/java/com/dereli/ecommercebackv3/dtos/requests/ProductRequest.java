package com.dereli.ecommercebackv3.dtos.requests;

import lombok.Data;

import java.util.Set;

@Data
public class ProductRequest {

    String code;

    String name;

    String description;

    String picture;

    Set<String> galleryImages;

    Integer stockValue;

    Boolean isApproved;

    Double price;

    String categoryCode;

    Set<String> keywords;

}
