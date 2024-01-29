package com.test.spring.security.jwt.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.spring.security.jwt.models.Products;
import com.test.spring.security.jwt.payload.request.ProductRequest;
import com.test.spring.security.jwt.payload.response.MessageResponse;
import com.test.spring.security.jwt.payload.response.ProductResponse;
import com.test.spring.security.jwt.repository.ProductRepository;
import com.test.spring.security.jwt.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<MessageResponse> getAllProducts() {
        MessageResponse messageResponse = new MessageResponse();
        List<Products> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
        messageResponse.setData(productResponses);
        messageResponse.setMessage("Get All products is successful");
        messageResponse.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(messageResponse);
    }

    @Override
    public ResponseEntity<MessageResponse> getProductById(Long id) {
        Products product = productRepository.findById(id).orElse(null);

        if (product != null) {
            ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setData(productResponse);
            messageResponse.setMessage("Get Product By id is successful");
            messageResponse.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok().body(messageResponse);
        } else {
            MessageResponse notFoundResponse = new MessageResponse();
            notFoundResponse.setMessage("Product not found with id: " + id);
            notFoundResponse.setStatus(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> createProduct(ProductRequest productRequest) {
        try {
            Products product = modelMapper.map(productRequest, Products.class);
            Products savedProduct = productRepository.save(product);
            ProductResponse productResponse = modelMapper.map(savedProduct, ProductResponse.class);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setData(productResponse);
            messageResponse.setMessage("Create Product successfully");
            messageResponse.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok().body(messageResponse);
        } catch (Exception e) {
            // Xử lý exception ở đây (ví dụ: log exception)
            e.printStackTrace();

            MessageResponse errorResponse = new MessageResponse();
            errorResponse.setMessage("Failed to create product. Please try again later.");
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateProduct(Long id, ProductRequest updatedProductRequest) {
        Products existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(updatedProductRequest.getName());
            existingProduct.setPrice(updatedProductRequest.getPrice());
            productRepository.save(existingProduct);

            ProductResponse updatedProductResponse = modelMapper.map(existingProduct, ProductResponse.class);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setData(updatedProductResponse);
            messageResponse.setMessage("Update Product successful");
            messageResponse.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok().body(messageResponse);
        } else {
            MessageResponse notFoundResponse = new MessageResponse();
            notFoundResponse.setMessage("Product not found");
            notFoundResponse.setStatus(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteProduct(Long id) {
        Products existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            productRepository.delete(existingProduct);

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage("Delete Product successful");
            messageResponse.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok().body(messageResponse);
        } else {
            MessageResponse notFoundResponse = new MessageResponse();
            notFoundResponse.setMessage("Product not found");
            notFoundResponse.setStatus(HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
    }
}
