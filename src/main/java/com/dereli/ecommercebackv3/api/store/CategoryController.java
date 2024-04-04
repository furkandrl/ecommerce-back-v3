package com.dereli.ecommercebackv3.api.store;

import com.dereli.ecommercebackv3.services.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/c")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/{categoryCode}")
    public ResponseEntity getCategoryPageByCode(@PathVariable String categoryCode, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "3") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return new ResponseEntity<>(categoryService.getCategoryPage(categoryCode, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getCategories() {
        try {
            return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
