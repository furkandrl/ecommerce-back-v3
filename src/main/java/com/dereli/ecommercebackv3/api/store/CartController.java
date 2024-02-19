package com.dereli.ecommercebackv3.api.store;

import com.dereli.ecommercebackv3.constants.DereliCoreConstants;
import com.dereli.ecommercebackv3.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping
    public ResponseEntity getCartForCustomer(){
        try {
            return new ResponseEntity<>(cartService.getCartForCustomer(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity addProductToCart(@RequestParam String productCode, @RequestParam Integer qty) {
        try {
            cartService.addProductToCart(productCode,qty);
            return new ResponseEntity<>(DereliCoreConstants.PRODUCT_ADDED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove-product")
    public ResponseEntity removeProductFromCart(@RequestParam String productCode) {
        try {
            cartService.removeProductFromCart(productCode);
            return new ResponseEntity<>(DereliCoreConstants.PRODUCT_REMOVED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update-qty")
    public ResponseEntity updateQuantityOfProduct(@RequestParam String productCode, @RequestParam Integer qty) {
        try {
            cartService.updateProductQuantity(productCode, qty);
            return new ResponseEntity<>(DereliCoreConstants.QTY_UPDATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
