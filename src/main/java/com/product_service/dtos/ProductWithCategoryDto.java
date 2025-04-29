package com.product_service.dtos;

public class ProductWithCategoryDto {
    private String id;
    private String productName;
    private String description;
    private Integer quantity;

    private CategoryDto category;

    public ProductWithCategoryDto(){}

    public ProductWithCategoryDto(String id, String productName, String description, Integer quantity, CategoryDto category) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
