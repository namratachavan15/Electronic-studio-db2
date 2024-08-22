package com.namrata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.namrata.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
