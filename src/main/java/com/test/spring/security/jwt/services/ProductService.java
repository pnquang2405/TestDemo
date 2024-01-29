package com.test.spring.security.jwt.services;

import org.springframework.http.ResponseEntity;

import com.test.spring.security.jwt.payload.request.ProductRequest;
import com.test.spring.security.jwt.payload.response.MessageResponse;

public interface ProductService {

    ResponseEntity<MessageResponse> getAllProducts();

    ResponseEntity<MessageResponse> getProductById(Long id);

    ResponseEntity<MessageResponse> createProduct(ProductRequest productRequest);

    ResponseEntity<MessageResponse> updateProduct(Long id, ProductRequest productRequest);

    ResponseEntity<MessageResponse> deleteProduct(Long id);
}
