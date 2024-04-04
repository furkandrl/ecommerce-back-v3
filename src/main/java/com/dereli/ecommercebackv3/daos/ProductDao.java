package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Category;
import com.dereli.ecommercebackv3.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {

    Optional<Product> getProductByCode(String code);

    Page<Product> getProductByCode(String code, Pageable pageable);

    List<Product> findProductsByCategory(Category category);

    Page<Product> findProductsByCategory(Category category, Pageable pageable);

    @Query("SELECT pk FROM Product ")
    List<Long> getAllProductId();

    Product getProductByPk(Long pk);
}
