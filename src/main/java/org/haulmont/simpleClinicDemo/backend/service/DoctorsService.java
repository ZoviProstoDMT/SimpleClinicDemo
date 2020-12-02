package org.haulmont.simpleClinicDemo.backend.service;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorsService {

    @Autowired
    DoctorsRepository repository;

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }
}
