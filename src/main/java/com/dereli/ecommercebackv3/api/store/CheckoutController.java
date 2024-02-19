package com.dereli.ecommercebackv3.api.store;


import com.dereli.ecommercebackv3.services.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Resource
    CheckoutService checkoutService;

    @GetMapping
    public ResponseEntity getCheckoutPage() {
        try {
            return new ResponseEntity<>(checkoutService.getCheckoutPage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
