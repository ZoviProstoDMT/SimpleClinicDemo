package org.haulmont.simpleClinicDemo.backend.service;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;
import org.haulmont.simpleClinicDemo.backend.dao.repository.PrescriptionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorsService {

    final DoctorsRepository doctorsRepository;
    final PrescriptionsRepository prescriptionsRepository;

    public DoctorsService(DoctorsRepository doctorsRepository, PrescriptionsRepository prescriptionsRepository) {
        this.doctorsRepository = doctorsRepository;
        this.prescriptionsRepository = prescriptionsRepository;
    }

    public Doctor findById(long id) {
       return doctorsRepository.findOne(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorsRepository.findAll();
    }

    public void delete(Doctor doctor) {
        doctorsRepository.delete(doctor);
    }

    public void save(Doctor doctor) {
        doctorsRepository.save(doctor);
    }

    public int getPrescriptionsCount(Doctor doctor) {
        return prescriptionsRepository.findAllPrescriptionsByDoctorId(doctor.getId()).size();
    }
}
