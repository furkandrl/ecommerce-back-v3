package com.dereli.ecommercebackv3.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category extends Item {

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String name;

    private String description;

}
