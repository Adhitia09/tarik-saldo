package com.crud_example.crud_java_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud_example.crud_java_springboot.model.Saldo;
import java.util.Optional;

public interface SaldoRepository extends JpaRepository<Saldo, Long> {
    Optional<Saldo> findFirstByOrderByIdAsc();
} 
