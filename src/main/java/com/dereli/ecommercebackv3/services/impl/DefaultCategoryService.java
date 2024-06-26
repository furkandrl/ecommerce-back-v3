package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.constants.Exceptions;
import com.dereli.ecommercebackv3.daos.CategoryDao;
import com.dereli.ecommercebackv3.daos.ProductDao;
import com.dereli.ecommercebackv3.dtos.requests.CategoryListRequest;
import com.dereli.ecommercebackv3.dtos.requests.CategoryRequest;
import com.dereli.ecommercebackv3.dtos.responses.CategoryResponse;
import com.dereli.ecommercebackv3.dtos.responses.ProductListResponse;
import com.dereli.ecommercebackv3.dtos.responses.ProductPageResponse;
import com.dereli.ecommercebackv3.dtos.responses.ProductResponse;
import com.dereli.ecommercebackv3.models.Category;
import com.dereli.ecommercebackv3.models.Product;
import com.dereli.ecommercebackv3.services.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCategoryService implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private ProductDao productDao;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryDao.findAll();
        return categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductPageResponse getCategoryPage(String code, Pageable pageable) throws Exception {

        ProductPageResponse response = new ProductPageResponse();

        Optional<Category> optionalCategory = categoryDao.getCategoryByCode(code);

        if(optionalCategory.isPresent()){
            response.setCategory(modelMapper.map(optionalCategory.get(), CategoryResponse.class));

            Page<Product> products = productDao.findProductsByCategory(optionalCategory.get(), pageable);
            if(!products.hasContent()){
                throw new Exception(Exceptions.PRODUCT_NOT_FOUND_FOR_CATEGORY + optionalCategory.get().getCode());
            }
            response.setCategory(modelMapper.map(optionalCategory.get(), CategoryResponse.class));
            List<ProductResponse> mappedProducts = products
                    .stream()
                    .map(product -> modelMapper.map(product, ProductResponse.class))
                    .collect(Collectors.toList());

            Page<ProductResponse> mappedPage = new PageImpl<>(mappedProducts, products.getPageable(), products.getTotalElements());
            response.setProducts(mappedPage);

            return response;

        } else {
            throw new Exception(Exceptions.CATEGORY_NOT_FOUND_CODE + code);
        }

    }

    @Override
    public Category getCategoryForCode(String code) {
        Optional<Category> optionalCategory = categoryDao.getCategoryByCode(code);
        return optionalCategory.orElse(null);
    }

    @Override
    public void createOrUpdateCategory(CategoryRequest dto) {
        Category category;
        Optional<Category> optionalCategory = categoryDao.getCategoryByCode(dto.getCode());
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            category = new Category();
            category.setCode(dto.getCode());
        }
        if (StringUtils.isNotBlank(dto.getName())) category.setName(dto.getName());
        if (StringUtils.isNotBlank(dto.getDescription())) category.setDescription(dto.getDescription());
        categoryDao.save(category);
    }

    @Override
    public void createOrUpdateCategoryBulk(CategoryListRequest categoryListRequest) {
        categoryListRequest.getCategories().forEach(categoryRequest -> {
            createOrUpdateCategory(categoryRequest);
        });
    }

}
