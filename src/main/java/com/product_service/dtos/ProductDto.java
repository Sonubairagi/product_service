package com.product_service.dtos;

public class ProductDto {
    private String id;
    private String productName;
    private String description;
    private Integer quantity;
    private String categoryId;

    public ProductDto(){}

    public ProductDto(String id, String productName, String description, Integer quantity, String categoryId) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.categoryId = categoryId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
