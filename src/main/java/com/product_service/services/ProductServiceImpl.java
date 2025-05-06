package com.product_service.services;

import com.product_service.dtos.CategoryDto;
import com.product_service.dtos.ProductDto;
import com.product_service.dtos.ProductWithCategoryDto;
import com.product_service.entities.Category;
import com.product_service.entities.Product;
import com.product_service.mappers.CategoryMapper;
import com.product_service.mappers.ProductMapper;
import com.product_service.repositories.CategoryRepository;
import com.product_service.repositories.ProductRepository;
import com.product_service.util.LoggerUtil;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerUtil.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, String categoryId) {
        try {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if(category.isPresent()){
                Product product = new Product();
                String productId = UUID.randomUUID().toString();
                product.setId(productId);
                product.setCategoryId(categoryId);
                product.setProductName(productDto.getProductName());
                product.setDescription(productDto.getDescription());
                product.setQuantity(productDto.getQuantity());

                Product saved = productRepository.save(product);
                log.info("product save successfully in DB");
                return ProductMapper.mapToProductDto(saved);
            }
            return null;
        } catch (Exception e) {
            log.error("Product not register during the process some error is occur : {} ", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProductWithCategoryDto> findAllProducts() {
        log.debug("Fetching all products with category...");

        try {
            List<Product> products = productRepository.findAll();

            List<ProductWithCategoryDto> result = products.stream().map(product -> {
                ProductWithCategoryDto dto = new ProductWithCategoryDto();
                dto.setId(product.getId());
                dto.setProductName(product.getProductName());
                dto.setDescription(product.getDescription());
                dto.setQuantity(product.getQuantity());

                // Fetch category by categoryId
                Optional<Category> optionalCategory = categoryRepository.findById(product.getCategoryId());
                optionalCategory.ifPresent(category -> {
                    CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(category);
                    dto.setCategory(categoryDto);
                });

                return dto;
            }).collect(Collectors.toList());

            log.info("Fetched all products with category successfully.");
            return result;

        } catch (Exception e) {
            log.error("Error while fetching products with category: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductDto getProduct(String id) {

        ProductDto productDto = null;

        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productDto = ProductMapper.mapToProductDto(product);
        }

        return productDto;
    }

    @Override
    public ProductWithCategoryDto getProductById(String productId) {
        log.debug("Fetching product with ID: {}", productId);

        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

            ProductWithCategoryDto dto = new ProductWithCategoryDto();
            dto.setId(product.getId());
            dto.setProductName(product.getProductName());
            dto.setDescription(product.getDescription());
            dto.setQuantity(product.getQuantity());

            // Fetch and map the category
            Optional<Category> optionalCategory = categoryRepository.findById(product.getCategoryId());
            optionalCategory.ifPresent(category -> {
                CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(category);
                dto.setCategory(categoryDto);
            });

            log.info("Successfully fetched product with ID: {}", productId);
            return dto;

        } catch (Exception e) {
            log.error("Error fetching product by ID {}: {}", productId, e.getMessage());
            throw new RuntimeException("Failed to fetch product by ID: " + productId, e);
        }
    }

    @Override
    public String deleteProductById(String productId) {
        try{
            boolean present = Optional.ofNullable(productId).isPresent();
            if(present){
                productRepository.deleteById(productId);
                return "Product details delete successfully by id : "+productId;
            }
            return null;
        }catch (Exception e){
            log.error("Error deleting product by ID {}: {}", productId, e.getMessage());
            throw new RuntimeException("Failed to delete product by ID: " + productId, e);
        }
    }


}
