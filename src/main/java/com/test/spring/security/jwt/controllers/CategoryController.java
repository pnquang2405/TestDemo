package com.test.spring.security.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.test.spring.security.jwt.payload.request.CategoryRequest;
import com.test.spring.security.jwt.payload.response.MessageResponse;
import com.test.spring.security.jwt.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public ResponseEntity<MessageResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    public ResponseEntity<MessageResponse> updateCategory(@PathVariable Long id,
            @RequestBody CategoryRequest updatedCategoryRequest) {
        return categoryService.updateCategory(id, updatedCategoryRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
