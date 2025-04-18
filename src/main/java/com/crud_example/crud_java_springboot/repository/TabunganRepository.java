package com.crud_example.crud_java_springboot.repository;

import com.crud_example.crud_java_springboot.model.Tabungan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabunganRepository extends JpaRepository<Tabungan, Long> {
    
}
