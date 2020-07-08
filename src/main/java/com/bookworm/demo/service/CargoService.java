package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Cargo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CargoService {

    List<Cargo> getCargos();

    void saveCargo(Cargo cargo);

    Cargo getCargo(int id) throws ResourceNotFoundException;

    void deleteCargo(int id) throws ResourceNotFoundException;

}
