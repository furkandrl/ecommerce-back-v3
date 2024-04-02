package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.models.Customer2ProductRating;
import com.dereli.ecommercebackv3.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface C2PDao extends JpaRepository<Customer2ProductRating, Long> {

    Set<Customer2ProductRating> findCustomer2ProductRatingsByCustomer (Customer customer);

    Customer2ProductRating findCustomer2ProductRatingsByCustomerAndAndProduct (Customer customer, Product product);

    @Query(value = "SELECT * FROM dereli.c2p_rating WHERE product_pk = {product.getPk()} NOT IN ", nativeQuery = true)
    Set<Customer2ProductRating> findCustomer2ProductRatingsByProduct(Product product);

    @Query(value = "SELECT * FROM dereli.c2p_rating cp WHERE cp.productPk = :productPk AND cp.customerPk != :customerPk", nativeQuery = true)
    Set<Customer2ProductRating> findByProductPkAndNotCustomerPk(@Param("productPk") Long productPk, @Param("customerPk") Long customerPk);

    @Query(value = "SELECT * FROM dereli.c2p_rating", nativeQuery = true)
    List<Customer2ProductRating> findAllC2PRatings();
}
