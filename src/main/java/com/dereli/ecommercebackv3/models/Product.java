package com.dereli.ecommercebackv3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product extends Item {

    @Column(unique = true)
    private String code;

    private String name;

    private String description;

    private String picture;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> galleryImages;

    private Integer stockValue;

    private Boolean isApproved;

    private Double price;

    @JsonBackReference
    @JoinColumn(name = "category", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords;

}
