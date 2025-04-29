package com.product_service.services;

import com.product_service.dtos.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    ArrayList<CategoryDto> findAllCategories();

    String deleteCategoryById(String categoryId);

    CategoryDto updateCategoryById(String categoryId, CategoryDto categoryDto);

    CategoryDto updateCategoryFieldById(String categoryId, CategoryDto categoryDto);

    CategoryDto getCategoryById(String categoryId);
}
