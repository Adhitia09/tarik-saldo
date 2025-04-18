package com.crud_example.crud_java_springboot.controller;

import java.util.List;
import java.util.Optional;

import com.crud_example.crud_java_springboot.model.SaldoDTO;
import com.crud_example.crud_java_springboot.service.SaldoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saldo")
public class SaldoController {
    private final SaldoService saldoService;

    public SaldoController(SaldoService saldoService) {
        this.saldoService = saldoService;
    }

    @GetMapping
    public List<SaldoDTO> getAllData() {
        return saldoService.getAllSaldo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaldoDTO> getDataById(@PathVariable Long id) {
        Optional<SaldoDTO> product = saldoService.getSaldoById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SaldoDTO createData(@RequestBody SaldoDTO saldoDTO) {
        return saldoService.saveSaldo(saldoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaldoDTO> updateData(@PathVariable Long id, @RequestBody SaldoDTO saldoDTO) {
        try {
            SaldoDTO updatedSaldo = saldoService.updateSaldo(id, saldoDTO);
            return ResponseEntity.ok(updatedSaldo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        saldoService.deleteSaldo(id);
        return ResponseEntity.noContent().build();
    }
}
