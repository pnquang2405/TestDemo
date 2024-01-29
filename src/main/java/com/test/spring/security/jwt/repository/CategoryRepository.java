package com.test.spring.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.spring.security.jwt.models.Categories;

public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
