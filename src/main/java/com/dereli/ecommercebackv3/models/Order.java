package com.dereli.ecommercebackv3.models;


import com.dereli.ecommercebackv3.enums.OrderStatus;
import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order extends Item {

    @OneToMany(fetch = FetchType.EAGER)
    @Nullable
    private Set<Entry> entries;

    private Double totalPriceOfProducts;

    @ManyToOne
    private Customer customer;

    private Double totalPrice;

    private OrderStatus orderStatus;

    @ManyToOne
    private Address address;

    private String shippingTrackingLink;

}
