package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.Cargo;
import com.bookworm.demo.repository.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @EJB
    private CargoRepository cargoRepository;

    @Override
    @Transactional
    public List<Cargo> getCargos() {
        return cargoRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCargo(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    @Override
    @Transactional
    public Cargo getCargo(int id) throws ResourceNotFoundException {
        return cargoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteCargo(int id) {
        cargoRepository.deleteById(id);
    }
}
