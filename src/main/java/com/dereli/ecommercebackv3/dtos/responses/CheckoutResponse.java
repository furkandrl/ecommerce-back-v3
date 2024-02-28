package com.dereli.ecommercebackv3.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class CheckoutResponse {

    private CartResponse cart;

    private List<AddressResponse> addresses;

    public CartResponse getCart() {
        return cart;
    }

    public void setCart(CartResponse cart) {
        this.cart = cart;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }

}
