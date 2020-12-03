package org.haulmont.simpleClinicDemo.backend.service;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorsService {

    final
    DoctorsRepository repository;

    public DoctorsService(DoctorsRepository repository) {
        this.repository = repository;
    }

    public Doctor findById(long id) {
       return repository.findOne(id);
    }

    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }

    public void delete(Doctor doctor) {
        repository.delete(doctor);
    }

    public void save(Doctor doctor) {
        repository.save(doctor);
    }
}
