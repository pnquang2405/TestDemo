package com.test.spring.security.jwt.services;

import org.springframework.http.ResponseEntity;

import com.test.spring.security.jwt.payload.request.CategoryRequest;
import com.test.spring.security.jwt.payload.response.MessageResponse;

public interface CategoryService {

    ResponseEntity<MessageResponse> createCategory(CategoryRequest categoryRequest);

    ResponseEntity<MessageResponse> getCategoryById(Long id);

    ResponseEntity<MessageResponse> getAllCategories();

    ResponseEntity<MessageResponse> updateCategory(Long id, CategoryRequest updatedCategoryRequest);

    ResponseEntity<MessageResponse> deleteCategory(Long id);
}
