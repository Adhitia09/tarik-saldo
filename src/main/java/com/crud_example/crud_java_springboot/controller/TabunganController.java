package com.crud_example.crud_java_springboot.controller;

import com.crud_example.crud_java_springboot.model.TabunganDTO;
import com.crud_example.crud_java_springboot.service.TabunganService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tabungan")
public class TabunganController {
    private final TabunganService tabunganService;

    public TabunganController(TabunganService tabunganService) {
        this.tabunganService = tabunganService;
    }

    @GetMapping
    public List<TabunganDTO> getAllData() {
        return tabunganService.getAllData();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TabunganDTO> getDataById(@PathVariable Long id) {
        Optional<TabunganDTO> product = tabunganService.getDataById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TabunganDTO createData(@RequestBody TabunganDTO tabunganDTO) {
        return tabunganService.saveData(tabunganDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TabunganDTO> updateData(@PathVariable Long id, @RequestBody TabunganDTO tabunganDTO) {
        try {
            TabunganDTO updatedData = tabunganService.updateData(id, tabunganDTO);
            return ResponseEntity.ok(updatedData);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        tabunganService.deleteData(id);
        return ResponseEntity.noContent().build();
    }
}
