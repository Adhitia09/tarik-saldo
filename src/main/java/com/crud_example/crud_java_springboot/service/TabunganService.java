package com.crud_example.crud_java_springboot.service;
import com.crud_example.crud_java_springboot.model.TabunganDTO;

import java.util.List;
import java.util.Optional;

public interface TabunganService {
    List<TabunganDTO> getAllData();
    Optional<TabunganDTO> getDataById(Long id);
    TabunganDTO saveData(TabunganDTO tabunganDTODTO);
    TabunganDTO updateData(Long id, TabunganDTO tabunganDTO);
    void deleteData(Long id);
}
