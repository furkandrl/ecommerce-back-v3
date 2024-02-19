package com.dereli.ecommercebackv3.dtos.requests;

import lombok.Data;

@Data
public class CustomerRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String password;

}
