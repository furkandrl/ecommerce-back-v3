package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Category;
import com.dereli.ecommercebackv3.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {

    Optional<Product> getProductByCode(String code);

    List<Product> findProductsByCategory(Category category);

}
