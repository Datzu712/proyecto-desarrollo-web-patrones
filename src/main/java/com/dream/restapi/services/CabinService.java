package com.dream.restapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dream.restapi.model.Cabin;
import com.dream.restapi.repository.CabinRepository;

@Service
public class CabinService {
    @Autowired
    private CabinRepository cabinRepository;

    public List<Cabin> getAllCabins() {
        return cabinRepository.findAll();
    }

    public Cabin getCabinById(int id) {
        return cabinRepository.findById(id).orElse(null);
    }

    public Cabin createOrUpdateCabin(Cabin cabin) {
        return cabinRepository.save(cabin);
    }

    public Cabin createOrUpdateCabin(int id, Cabin cabin) {
        if (cabinRepository.existsById(id)) {
            cabin.setId(id);
            return cabinRepository.save(cabin);
        }
        throw new RuntimeException("Cabin not found with id: " + id);
    }

    public boolean deleteCabin(int id) {
        Optional<Cabin> cabin = cabinRepository.findById(id);
        if (cabin.isPresent()) {
            cabinRepository.delete(cabin.get());
            return true;
        }
        return false;
    }
}
