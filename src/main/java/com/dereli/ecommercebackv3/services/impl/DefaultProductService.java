package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.constants.Exceptions;
import com.dereli.ecommercebackv3.daos.ModelDao;
import com.dereli.ecommercebackv3.daos.ProductDao;
import com.dereli.ecommercebackv3.dtos.requests.ProductListRequest;
import com.dereli.ecommercebackv3.dtos.requests.ProductRequest;
import com.dereli.ecommercebackv3.dtos.responses.ProductListResponse;
import com.dereli.ecommercebackv3.dtos.responses.ProductResponse;
import com.dereli.ecommercebackv3.helpers.RatingHelper;
import com.dereli.ecommercebackv3.models.Category;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.models.Product;
import com.dereli.ecommercebackv3.services.CategoryService;
import com.dereli.ecommercebackv3.services.ProductService;
import com.dereli.ecommercebackv3.services.SessionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultProductService implements ProductService {

    @Resource
    private ProductDao productDao;

    @Resource
    private ModelDao modelDao;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SessionService sessionService;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private RatingHelper ratingHelper;

    @Override
    public Product findProductByCode(String code) throws Exception {
        Optional<Product> optionalProduct = productDao.getProductByCode(code);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            throw new Exception(Exceptions.PRODUCT_NOT_FOUND_CODE + code);
        }
    }

    @Override
    public double getAvgRatingForProduct(Long pk) throws Exception {
        double avgRating = productDao.getAvgRatingByProductPk(pk);
        if(avgRating > 0) return avgRating;
        return 0;
    }

    @Override
    public ProductListResponse getSimilarProducts(String code) throws Exception {
        Product product = this.findProductByCode(code);
        List<Product> similarProducts = ratingHelper.getSimilarProducts(product);
        List<ProductResponse> similarProductsResponse = similarProducts.stream().map(product1 -> modelMapper.map(product1, ProductResponse.class)).collect(Collectors.toList());
        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setProducts(similarProductsResponse);
        return productListResponse;
    }

    @Override
    public ProductListResponse getRecommendedProducts() {
        Customer customer = sessionService.getCurrentCustomer();
        if (Objects.nonNull(customer)) {
            List<Product> recommendedProducts = ratingHelper.getRecommendedProductsForCustomer(customer);
            List<ProductResponse> productResponses = Objects.nonNull(recommendedProducts) ? recommendedProducts
                    .stream()
                    .map(product -> modelMapper.map(product, ProductResponse.class))
                    .collect(Collectors.toList()) : null;
            ProductListResponse productListResponse = new ProductListResponse();
            productListResponse.setProducts(productResponses);
            return productListResponse;
        } else {
            List<Product> products = ratingHelper.getMostPopularProducts();
            List<ProductResponse> productResponses = Objects.nonNull(products) ? products.subList(0, Math.min(products.size(), 8))
                    .stream()
                    .map(product -> modelMapper.map(product, ProductResponse.class))
                    .collect(Collectors.toList()) : null;
            ProductListResponse productListResponse = new ProductListResponse();
            productListResponse.setProducts(productResponses);
            return productListResponse;
        }
    }

    @Override
    public ProductListResponse getFavoriteProducts() {
        Customer customer = sessionService.getCurrentCustomer();
        Set<Product> favProducts = customer.getFavProducts();
        List<ProductResponse> productResponseList = favProducts.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setProducts(productResponseList);
        return productListResponse;
    }

    @Override
    public List<ProductResponse> getBestSellingProducts() {
        return null;
    }

    @Override
    public void createOrUpdateProductBulk(ProductListRequest dto) throws Exception {
        dto.getProducts().forEach(productRequest -> {
            try {
                createOrUpdateProduct(productRequest);
            } catch (Exception e) {
                // TODO
            }
        });
    }

    @Override
    public void deleteProductForCode(String code) throws Exception {
        Optional<Product> optionalProduct = productDao.getProductByCode(code);
        if (optionalProduct.isPresent()) {
            productDao.delete(optionalProduct.get());
        } else {
            throw new Exception("Product not found for code: " + code);
        }
    }


    @Override
    public void createOrUpdateProduct(ProductRequest dto) throws Exception {
        Product product;
        Optional<Product> optionalProduct = productDao.getProductByCode(dto.getCode());
        if (optionalProduct.isPresent()){
            product = optionalProduct.get();
        } else {
            product = new Product();
            product.setCode(dto.getCode());
        }
        if (StringUtils.isBlank(dto.getCategoryCode())) {
            throw new Exception("Category code can not be blank!");
        }
        Category category = categoryService.getCategoryForCode(dto.getCategoryCode());
        if (category != null) {
            product.setCategory(category);
        } else {
            throw new Exception("Category not found for : " + dto.getCategoryCode());
        }
        if (StringUtils.isNotBlank(dto.getName())) {
            product.setName(dto.getName());
        }
        if (StringUtils.isNotBlank(dto.getDescription())) {
            product.setDescription(dto.getDescription());
        }
        if (StringUtils.isNotBlank(dto.getPicture())) {
            product.setPicture(dto.getPicture());
        }
        if (CollectionUtils.isNotEmpty(dto.getGalleryImages())) {
            product.setGalleryImages(dto.getGalleryImages());
        }
        if (CollectionUtils.isNotEmpty(dto.getKeywords())) {
            product.setKeywords(dto.getKeywords());
        }
        if (Objects.nonNull(dto.getStockValue())) {
            product.setStockValue(dto.getStockValue());
        }
        if (Objects.nonNull(dto.getPrice())) {
            product.setPrice(dto.getPrice());
        }
        if (Objects.nonNull(dto.getIsApproved())) {
            product.setIsApproved(dto.getIsApproved());
        }
        productDao.save(product);
        List<Product> productsInSameCategory = this.getProductModelsForCategory(category);
        ratingHelper.createOrRecalculateRatingOfP2P(productsInSameCategory, product, 4D, 15);
    }

    @Override
    public List<Product> getProductModelsForCategory(Category category) {
        return productDao.findProductsByCategory(category);
    }

    @Override
        public void addProductToFavorites(String productCode) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Set<Product> favProducts = customer.getFavProducts();
        favProducts.add(this.findProductByCode(productCode));
        customer.setFavProducts(favProducts);
        modelDao.save(customer);
    }
}
