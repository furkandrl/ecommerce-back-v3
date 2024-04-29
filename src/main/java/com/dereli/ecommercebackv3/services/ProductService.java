package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.requests.ProductListRequest;
import com.dereli.ecommercebackv3.dtos.requests.ProductRequest;
import com.dereli.ecommercebackv3.dtos.responses.ProductListResponse;
import com.dereli.ecommercebackv3.dtos.responses.ProductResponse;
import com.dereli.ecommercebackv3.models.Category;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product findProductByCode(String code) throws Exception;

    double getAvgRatingForProduct(Long pk) throws Exception;

    double getCustomerGivenStar(Customer customer);
    ProductListResponse getSimilarProducts(String code) throws Exception;

    ProductListResponse getRecommendedProducts();

    ProductListResponse getFavoriteProducts();

    List<ProductResponse> getBestSellingProducts();

    void createOrUpdateProductBulk(ProductListRequest dto) throws Exception;

    void deleteProductForCode(String code) throws Exception;

    void createOrUpdateProduct(ProductRequest dto) throws Exception;

    List<Product> getProductModelsForCategory(Category category);

    void addProductToFavorites(String productCode) throws Exception;

}
