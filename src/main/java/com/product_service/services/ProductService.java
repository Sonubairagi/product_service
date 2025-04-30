package com.product_service.services;

import com.product_service.dtos.ProductDto;
import com.product_service.dtos.ProductWithCategoryDto;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, String categoryId);

    List<ProductWithCategoryDto> findAllProducts();

    ProductDto getProduct(String id);
}
