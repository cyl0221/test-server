package com.example.test_server.repository;

import com.example.test_server.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;



public interface ProductsRepository extends JpaRepository<Products, Long>,
      QuerydslPredicateExecutor<Products>{

  }
