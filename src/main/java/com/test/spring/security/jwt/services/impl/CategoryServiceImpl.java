package com.test.spring.security.jwt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.spring.security.jwt.models.Categories;
import com.test.spring.security.jwt.payload.request.CategoryRequest;
import com.test.spring.security.jwt.payload.response.CategoryResponse;
import com.test.spring.security.jwt.payload.response.MessageResponse;
import com.test.spring.security.jwt.repository.CategoryRepository;
import com.test.spring.security.jwt.services.CategoryService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<MessageResponse> createCategory(CategoryRequest categoryRequest) {
        Categories category = modelMapper.map(categoryRequest, Categories.class);
        Categories savedCategory = categoryRepository.save(category);
        CategoryResponse categoryResponse = modelMapper.map(savedCategory, CategoryResponse.class);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setData(categoryResponse);
        messageResponse.setMessage("Create Category successfully");
        messageResponse.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok(messageResponse);
    }

    @Override
    public ResponseEntity<MessageResponse> getCategoryById(Long id) {
        Categories category = categoryRepository.findById(id).orElse(null);

        if (category != null) {
            CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setData(categoryResponse);
            messageResponse.setMessage("Get Category By id is successful");
            messageResponse.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok(messageResponse);
        } else {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Category not found");
            messageResponse.setStatus(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> getAllCategories() {
        List<Categories> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setData(categoryResponses);
        messageResponse.setMessage("Get All Categories is successful");
        messageResponse.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok(messageResponse);
    }

    @Override
    public ResponseEntity<MessageResponse> updateCategory(Long id, CategoryRequest updatedCategoryRequest) {
        Categories existingCategory = categoryRepository.findById(id).orElse(null);

        if (existingCategory != null) {
            modelMapper.map(updatedCategoryRequest, existingCategory);
            Categories savedCategory = categoryRepository.save(existingCategory);
            CategoryResponse categoryResponse = modelMapper.map(savedCategory, CategoryResponse.class);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setData(categoryResponse);
            messageResponse.setMessage("Update Category successfully");
            messageResponse.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok(messageResponse);
        } else {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Category not found");
            messageResponse.setStatus(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Delete Category successfully");
            messageResponse.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok(messageResponse);
        } else {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Category not found");
            messageResponse.setStatus(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }
    }
}
