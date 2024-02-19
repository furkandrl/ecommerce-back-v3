package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.responses.CartResponse;
import com.dereli.ecommercebackv3.models.Cart;

public interface CartService {

    CartResponse getCartForCustomer();
    void validateCart(Cart cart);
    void calculateCart(Cart cart);
    void addProductToCart(String productCode, Integer qty) throws Exception;
    void removeProductFromCart(String productCode) throws Exception;
    void updateProductQuantity(String productCode, Integer qty) throws Exception;

}
