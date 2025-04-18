package com.crud_example.crud_java_springboot.service;

import com.crud_example.crud_java_springboot.model.*;
import com.crud_example.crud_java_springboot.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TabunganServiceImpl implements TabunganService {

    private final TabunganRepository tabunganRepository;
    private final SaldoRepository saldoRepository;

    public TabunganServiceImpl(TabunganRepository tabunganRepository, SaldoRepository saldoRepository) {
        this.tabunganRepository = tabunganRepository;
        this.saldoRepository = saldoRepository;
    }

    @Override
    public List<TabunganDTO> getAllData() {
        return tabunganRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TabunganDTO> getDataById(Long id) {
        return tabunganRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    @Override
    public TabunganDTO saveData(TabunganDTO tabunganDTO) {
        // 1. Ambil saldo saat ini (asumsi hanya ada 1 data saldo)
        Saldo saldo = saldoRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Saldo tidak ditemukan"));

        // 2. Cek apakah saldo cukup
        if (saldo.getPrice().compareTo(tabunganDTO.price()) < 0) {
            throw new RuntimeException("Saldo tidak mencukupi");
        }

        // 3. Kurangi saldo
        saldo.setPrice(saldo.getPrice().subtract(tabunganDTO.price()));
        saldoRepository.save(saldo);

        // 4. Simpan histori penarikan
        Tabungan tabungan = convertToEntity(tabunganDTO);
        Tabungan savedData = tabunganRepository.save(tabungan);
        return convertToDTO(savedData);
    }

    @Override
    public TabunganDTO updateData(Long id, TabunganDTO tabunganDTO) {
        Tabungan tabungan = tabunganRepository.findById(id).orElseThrow();
        tabungan.setDescription(tabunganDTO.description());
        tabungan.setPrice(tabunganDTO.price());
        Tabungan updatedData = tabunganRepository.save(tabungan);
        return convertToDTO(updatedData);
    }

    @Override
    public void deleteData(Long id) {
        tabunganRepository.deleteById(id);
    }

    // Convert tabungan Entity to TabunganDTO
    private TabunganDTO convertToDTO(Tabungan tabungan) {
        return new TabunganDTO(tabungan.getId(), tabungan.getDescription(), tabungan.getPrice());
    }

    // Convert TabunganDTO to tabungan Entity
    private Tabungan convertToEntity(TabunganDTO tabunganDTO) {
        Tabungan tabungan = new Tabungan();
        tabungan.setDescription(tabunganDTO.description());
        tabungan.setPrice(tabunganDTO.price());
        return tabungan;
    }
}
