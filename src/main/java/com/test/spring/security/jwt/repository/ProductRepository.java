package com.test.spring.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.spring.security.jwt.models.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
