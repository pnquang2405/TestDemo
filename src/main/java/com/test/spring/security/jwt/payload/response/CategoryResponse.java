package com.test.spring.security.jwt.payload.response;

public class CategoryResponse {
    private String name;

    public CategoryResponse(String name) {
        this.name = name;
    }

    public CategoryResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
