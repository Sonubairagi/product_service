package com.product_service.controllers;

import com.product_service.dtos.CategoryDto;
import com.product_service.util.LoggerUtil;
import com.product_service.services.CategoryService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("category/v1/api")
public class CategoryController {

    private static final Logger log = LoggerUtil.getLogger(CategoryController.class);

    private final CategoryService categoryService;
    private CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //    http://localhost:8080/category/v1/api/addCategory
    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto> categoryCreation(
            @RequestBody CategoryDto categoryDto
    ){
        log.debug("Category creation state..");
        CategoryDto category = null;
        try{
            category = categoryService.createCategory(categoryDto);
            if (category != null){
                log.info("Category register successfully by id : {}", category.getId());
                return new ResponseEntity<>(category, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(category, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Category not register : {}", e.getMessage());
            return new ResponseEntity<>(category, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    http://localhost:8080/category/v1/api/getAllCategories
    @GetMapping("/getAllCategories")
    public ResponseEntity<ArrayList<CategoryDto>> getAllCategories(){
        log.debug("Get All Categories state..");
        ArrayList<CategoryDto> categoryList = null;
        try{
            categoryList = categoryService.findAllCategories();
            if (categoryList != null){
                log.info("Finding the Categories successfully!");
                return new ResponseEntity<>(categoryList, HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryList, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Category not getting : {}", e.getMessage());
            return new ResponseEntity<>(categoryList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    http://localhost:8080/category/v1/api/deleteCategory/{categoryId}
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable("categoryId") String categoryId
    ){
        String deleteCategory = null;
        try{
            log.debug("Delete Category By ID : {}",categoryId);
            deleteCategory = categoryService.deleteCategoryById(categoryId);
            if(deleteCategory != null){
                log.info("Delete Category by id : {} is successfully!",categoryId);
                return new ResponseEntity<>(deleteCategory, HttpStatus.OK);
            }
            log.debug("Category not fount By ID : {}",categoryId);
            return new ResponseEntity<>("Category not found by id : " + categoryId, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Category not deleting : {}", e.getMessage());
            return new ResponseEntity<>("Something went the wrong category not delete by id : "+categoryId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    http://localhost:8080/category/v1/api/updateCategory/{categoryId}
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody CategoryDto categoryDto
    ){
        CategoryDto updateCategory = null;
        try{
            log.debug("Update Category By ID : {}",categoryId);
            if(categoryId != null && categoryDto != null){
                updateCategory = categoryService.updateCategoryById(categoryId,categoryDto);
                boolean present = Optional.ofNullable(updateCategory).isPresent();
                if(present){
                    log.info("update successfully by id : {}",categoryId);
                    return new ResponseEntity<>(updateCategory, HttpStatus.OK);
                }
            }
            log.debug("Category not found By ID : {}",categoryId);
            return new ResponseEntity<>(updateCategory, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Category not updating : {}", e.getMessage());
            return new ResponseEntity<>(updateCategory, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    http://localhost:8080/category/v1/api/updateCategoryField/{categoryId}
    @PatchMapping("/updateCategoryField/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategoryField(
            @PathVariable("categoryId") String categoryId,
            @RequestBody CategoryDto categoryDto
    ){
        CategoryDto updateCategoryField = null;
        try{
            log.debug("Update Category field By ID : {}",categoryId);
            if(categoryId != null && categoryDto != null){
                updateCategoryField = categoryService.updateCategoryFieldById(categoryId,categoryDto);
                boolean present = Optional.ofNullable(updateCategoryField).isPresent();
                if(present){
                    log.info("update category field successfully by id : {}",categoryId);
                    return new ResponseEntity<>(updateCategoryField, HttpStatus.OK);
                }
            }
            log.debug("Category field not found By ID : {}",categoryId);
            return new ResponseEntity<>(updateCategoryField, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Category field not updating : {}", e.getMessage());
            return new ResponseEntity<>(updateCategoryField, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    http://localhost:8080/category/v1/api/getCategoryById/{categoryId}
    @GetMapping("getCategoryById/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategoryField(
            @PathVariable String categoryId
    ){
        CategoryDto category = null;
        try{
            log.debug("Get Category detail By ID : {}",categoryId);
            if(categoryId != null){
                category = categoryService.getCategoryById(categoryId);
                log.info("Get category details successfully by id : {}",categoryId);
                return new ResponseEntity<>(category, HttpStatus.OK);
            }
            log.debug("Category detail not found By ID : {}",categoryId);
            return new ResponseEntity<>(category, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Category detail not getting : {}", e.getMessage());
            return new ResponseEntity<>(category, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

