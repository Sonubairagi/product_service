package com.product_service.controllers;


import com.product_service.dtos.CategoryDto;
import com.product_service.dtos.ProductDto;
import com.product_service.dtos.ProductWithCategoryDto;
import com.product_service.services.ProductService;
import com.product_service.util.LoggerUtil;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product/v1/api")
public class ProductController {
    private static final Logger log = LoggerUtil.getLogger(ProductController.class);

    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //    http://localhost:8080/product/v1/api/addProduct/{categoryId}
    @PostMapping("/addProduct/{categoryId}")
    public ResponseEntity<ProductDto> productAdding(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
    ){
        log.debug("Product creation state..");
        ProductDto product = null;
        try{
            product = productService.createProduct(productDto,categoryId);
            if (product != null){
                log.info("Product register successfully by id : {}", product.getId());
                return new ResponseEntity<>(product, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Product not register : {}", e.getMessage());
            return new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    http://localhost:8080/product/v1/api/getAllProducts
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductWithCategoryDto>> getAllProducts(){
        log.debug("Get All Products state..");
        List<ProductWithCategoryDto> productsList = null;
        try{
            productsList = productService.findAllProducts();
            if (productsList != null){
                log.info("Finding the products successfully!");
                return new ResponseEntity<>(productsList, HttpStatus.OK);
            }
            return new ResponseEntity<>(productsList, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Products not getting : {}", e.getMessage());
            return new ResponseEntity<>(productsList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/product/v1/api/getProductById/{productId}
    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductWithCategoryDto> getProductById(
            @PathVariable("productId") String productId
    ){
        ProductWithCategoryDto product = null;
        try{
            boolean present = Optional.ofNullable(productId).isPresent();
            if(present){
                product = productService.getProductById(productId);
                log.info("get product details successfully by id : {}",productId);
                return new ResponseEntity<>(product,HttpStatus.OK);
            }
            log.debug("not get the product value by id {}",productId);
            return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Product not getting : {}", e.getMessage());
            return new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/product/v1/api/deleteProduct/{productId}
    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("productId") String productId
    ){
        String productDelete = null;
        try{
            boolean present = Optional.ofNullable(productId).isPresent();
            if(present){
                productDelete = productService.deleteProductById(productId);
                log.info("delete product details successfully by id : {}",productId);
                return new ResponseEntity<>(productDelete,HttpStatus.OK);
            }
            log.debug("not delete the product value by id {}",productId);
            return new ResponseEntity<>(productDelete,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Product not delete : {}", e.getMessage());
            return new ResponseEntity<>(productDelete, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
