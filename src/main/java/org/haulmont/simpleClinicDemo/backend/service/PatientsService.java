package org.haulmont.simpleClinicDemo.backend.service;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.haulmont.simpleClinicDemo.backend.dao.repository.PatientsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientsService {

    final
    PatientsRepository repository;

    public PatientsService(PatientsRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public Patient findById(long id) {
        return repository.findOne(id);
    }

    public void delete(Patient patient) {
        repository.delete(patient);
    }

    public void save(Patient patient) {
        repository.save(patient);
    }
}
