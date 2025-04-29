package com.product_service.mappers;

import com.product_service.dtos.ProductDto;
import com.product_service.dtos.ProductWithCategoryDto;
import com.product_service.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductDto mapToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategoryId(product.getCategoryId());
        return productDto;
    }

    public static Product mapToProduct(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setProductName(product.getProductName());
        product.setDescription(product.getDescription());
        product.setQuantity(product.getQuantity());
        product.setCategoryId(product.getCategoryId());
        return product;
    }

    public static ProductWithCategoryDto mapToProductWithCategoryDto(Product product){
        ProductWithCategoryDto productDto = new ProductWithCategoryDto();
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());

//        productDto.setCategory([product.getId());

        return productDto;
    }

}
