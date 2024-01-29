package com.test.spring.security.jwt.payload.response;

import java.math.BigDecimal;

public class ProductResponse {
    private String nameProduct;
    private BigDecimal price;
    private String description;
    private Long categoryID;

    public ProductResponse() {
    }

    public ProductResponse(String nameProduct, BigDecimal price, String description, Long categoryID) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.description = description;
        this.categoryID = categoryID;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

}
