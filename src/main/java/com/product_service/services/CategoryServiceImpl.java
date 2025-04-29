package com.product_service.services;

import com.product_service.dtos.CategoryDto;
import com.product_service.entities.Category;
import com.product_service.mappers.CategoryMapper;
import com.product_service.util.LoggerUtil;
import com.product_service.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerUtil.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       try{
           log.debug("create category method run..");
           if (categoryDto != null){
               log.info("category object values are present");

               String categoryId = UUID.randomUUID().toString();
               categoryDto.setId(categoryId);

               Category category = CategoryMapper.mapToCategory(categoryDto);
               Category save = categoryRepository.save(category);

               log.info("category register in database successfully by ID: {}", category.getId());
               return CategoryMapper.mapToCategoryDto(save);
           }
       }catch (Exception e){
           log.error("Category not register during the process some error is occur : {} ", e.getMessage());
           return null;
       }
        return null;
    }

    @Override
    public ArrayList<CategoryDto> findAllCategories() {
        log.debug("get category method run..");
        try{
            List<Category> categories = categoryRepository.findAll();
            //class type casting
            log.info("fetch all the categories..");
            return (ArrayList<CategoryDto>) categories.stream().map(CategoryMapper::mapToCategoryDto).collect(Collectors.toList());
        }catch (Exception e){
            log.error("Categories not getting during the process some error is occur : {} ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public String deleteCategoryById(String categoryId) {
        try{
            log.debug("delete category by id : {}",categoryId);

            boolean present = categoryRepository.findById(categoryId).isPresent();
            if (present){
                categoryRepository.deleteById(categoryId);
                return "delete category by id : " + categoryId;
            }
            return null;
        }catch (Exception e){
            log.error("Categories not deleting during the process some error is occur : {} ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategoryById(String categoryId, CategoryDto categoryDto) {
        try{
            log.debug("Category update by id : {}",categoryId);
            boolean present = Optional.ofNullable(categoryId).isPresent();
            if(present){
                Optional<Category> categoryObj = categoryRepository.findById(categoryId);
                if(categoryObj.isPresent()){
                    log.info("category update values set...");
                    Category category = categoryObj.get();
                    category.setId(categoryId);
                    category.setName(categoryDto.getName());
                    category.setDescription(categoryDto.getDescription());
                    log.info("category updated by id : {}",categoryId);
                    Category saved = categoryRepository.save(category);
                    return CategoryMapper.mapToCategoryDto(saved);
                }
            }
            return null;
        }catch (Exception e){
            log.error("Categories not updating during the process some error is occur : {} ", e.getMessage());
            return null;
        }
    }

    @Override
    public CategoryDto updateCategoryFieldById(String categoryId, CategoryDto categoryDto) {
        try{
            log.debug("Category fields update by id : {}",categoryId);
            boolean present = Optional.ofNullable(categoryId).isPresent();
            if(present){
                Optional<Category> categoryFields = categoryRepository.findById(categoryId);
                if(categoryFields.isPresent()){
                    log.info("category field update values set...");
                    Category category = categoryFields.get();
                    category.setId(categoryId);

                    // Only update if the fields are non-null (PATCH behavior)
                    if (categoryDto.getName() != null)
                        category.setName(categoryDto.getName());
                    if (categoryDto.getDescription() != null)
                        category.setDescription(categoryDto.getDescription());

                    log.info("category field updated by id : {}",categoryId);
                    Category saved = categoryRepository.save(category);
                    return CategoryMapper.mapToCategoryDto(saved);
                }
            }
            return null;
        }catch (Exception e){
            log.error("Category not updating during the process some error is occur : {} ", e.getMessage());
            return null;
        }
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        log.debug("Category finding by id : {}",categoryId);
        try{
            boolean present = Optional.ofNullable(categoryId).isPresent();
            if(present){
                Optional<Category> category = categoryRepository.findById(categoryId);
                log.info("category getting...");
                return category.map(CategoryMapper::mapToCategoryDto).orElse(null);
            }
            return null;
        }catch (Exception e){
            log.error("Category not getting during the process some error is occur : {} ", e.getMessage());
            return null;
        }
    }
}
