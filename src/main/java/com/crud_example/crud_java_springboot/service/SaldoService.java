package com.crud_example.crud_java_springboot.service;

import com.crud_example.crud_java_springboot.model.SaldoDTO;
import java.util.List;
import java.util.Optional;

public interface SaldoService {
    List<SaldoDTO> getAllSaldo();
    Optional<SaldoDTO> getSaldoById(Long id);
    SaldoDTO saveSaldo(SaldoDTO saldoDTO);
    SaldoDTO updateSaldo(Long id, SaldoDTO saldoDTO);
    void deleteSaldo(Long id);
} 
