package com.test.spring.security.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.spring.security.jwt.payload.request.ProductRequest;
import com.test.spring.security.jwt.payload.response.MessageResponse;
import com.test.spring.security.jwt.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public ResponseEntity<MessageResponse> createProduct(@RequestBody ProductRequest product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<MessageResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("")
    public ResponseEntity<MessageResponse> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

}
