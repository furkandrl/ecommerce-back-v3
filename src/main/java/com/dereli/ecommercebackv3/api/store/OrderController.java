package com.dereli.ecommercebackv3.api.store;

import com.dereli.ecommercebackv3.constants.DereliCoreConstants;
import com.dereli.ecommercebackv3.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @Resource
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getOrdersForCustomer() {
        try {
            return new ResponseEntity<>(orderService.getOrdersForCustomer(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{orderCode}")
    public ResponseEntity getOrderByCode(@PathVariable String orderCode) {
        try {
            return new ResponseEntity<>(orderService.findOrderDetailsForCode(orderCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/place")
    public ResponseEntity placeOrder() {
        try {
            orderService.placeOrder();
            return new ResponseEntity<>(DereliCoreConstants.ORDER_PLACED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
