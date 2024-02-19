package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> getCategoryByCode(String code);

}
