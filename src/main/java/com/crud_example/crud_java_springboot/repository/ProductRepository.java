package com.crud_example.crud_java_springboot.repository;

import com.crud_example.crud_java_springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
