package com.dereli.ecommercebackv3.api.store;

import com.dereli.ecommercebackv3.constants.DereliCoreConstants;
import com.dereli.ecommercebackv3.dtos.responses.ProductResponse;
import com.dereli.ecommercebackv3.models.Product;
import com.dereli.ecommercebackv3.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/p")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping("/{productCode}")
    public ResponseEntity getProductPageByCode(@PathVariable String productCode){
        try {
            Product productModel = productService.findProductByCode(productCode);
            productModel.setAvgRating((productService.getAvgRatingForProduct(productModel.getPk()) / 10 *5));
            return new ResponseEntity<>(modelMapper.map(productModel, ProductResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/similar/{productCode}")
    public ResponseEntity getSimilarProducts(@PathVariable String productCode){
        try {
            return new ResponseEntity<>(productService.getSimilarProducts(productCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recommendations")
    public ResponseEntity getRecommendedProducts() {
        try {
            return new ResponseEntity(productService.getRecommendedProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-to-fav/{productCode}")
    public ResponseEntity addToFavorites(@PathVariable String productCode){
        try {
            productService.addProductToFavorites(productCode);
            return new ResponseEntity<>(modelMapper.map(DereliCoreConstants.PRODUCT_ADDED_SUCCESSFULLY, ProductResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity getFavoriteProducts() {
        try {
            return new ResponseEntity(productService.getFavoriteProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
