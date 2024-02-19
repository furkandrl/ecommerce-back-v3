package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.requests.CategoryListRequest;
import com.dereli.ecommercebackv3.dtos.requests.CategoryRequest;
import com.dereli.ecommercebackv3.dtos.responses.CategoryResponse;
import com.dereli.ecommercebackv3.dtos.responses.ProductListResponse;
import com.dereli.ecommercebackv3.models.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getCategories();

    ProductListResponse getCategoryPage(String code) throws Exception;

    Category getCategoryForCode(String code);

    void createOrUpdateCategory(CategoryRequest categoryRequest);

    void createOrUpdateCategoryBulk(CategoryListRequest categoryListRequest);

}
