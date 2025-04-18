package com.crud_example.crud_java_springboot.service;

import com.crud_example.crud_java_springboot.model.Saldo;
import com.crud_example.crud_java_springboot.model.SaldoDTO;
import com.crud_example.crud_java_springboot.repository.SaldoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class SaldoServiceImpl implements SaldoService {

    private final SaldoRepository saldoRepository;

    public SaldoServiceImpl(SaldoRepository saldoRepository) {
        this.saldoRepository = saldoRepository;
    }

    @Override
    public List<SaldoDTO> getAllSaldo() {
        return saldoRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<SaldoDTO> getSaldoById(Long id) {
        return saldoRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public SaldoDTO saveSaldo(SaldoDTO saldoDTO) {
        Saldo saldo = convertToEntity(saldoDTO);
        Saldo savedSaldo = saldoRepository.save(saldo);
        return convertToDTO(savedSaldo);
    }

    @Override
    public SaldoDTO updateSaldo(Long id, SaldoDTO saldoDTO) {
        Saldo saldo = saldoRepository.findById(id).orElseThrow();
        saldo.setPrice(saldoDTO.price());
        Saldo updatedSaldo = saldoRepository.save(saldo);
        return convertToDTO(updatedSaldo);
    }

    @Override
    public void deleteSaldo(Long id) {
        saldoRepository.deleteById(id);
    }

    // Convert Product Entity to ProductDTO
    private SaldoDTO convertToDTO(Saldo saldo) {
        return new SaldoDTO(saldo.getId(),saldo.getPrice());
    }

    // Convert ProductDTO to Product Entity
    private Saldo convertToEntity(SaldoDTO saldoDTO) {
        Saldo saldo = new Saldo();
        saldo.setPrice(saldoDTO.price());
        return saldo;
    }
}
