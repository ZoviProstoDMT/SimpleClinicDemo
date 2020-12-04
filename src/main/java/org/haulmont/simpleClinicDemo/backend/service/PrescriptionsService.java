package org.haulmont.simpleClinicDemo.backend.service;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Prescription;
import org.haulmont.simpleClinicDemo.backend.dao.repository.PrescriptionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionsService {

    final PatientsService patientsService;
    final DoctorsService doctorsService;
    final PrescriptionsRepository prescriptionsRepository;

    public PrescriptionsService(PatientsService patientsService, DoctorsService doctorsService, PrescriptionsRepository prescriptionsRepository) {
        this.patientsService = patientsService;
        this.doctorsService = doctorsService;
        this.prescriptionsRepository = prescriptionsRepository;
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionsRepository.findAll();
    }

    public Doctor getDoctorById(long id) {
        return doctorsService.findById(id);
    }

    public Patient getPatientById(long id) {
        return patientsService.findById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorsService.getAllDoctors();
    }

    public List<Patient> getAllPatients() {
        return patientsService.getAllPatients();
    }

    public void delete(Prescription prescription) {
        prescriptionsRepository.delete(prescription);
    }

    public void save(Prescription prescription) {
        prescriptionsRepository.save(prescription);
    }

    public List<Prescription> findAllPrescriptionsByDoctorId(long doctorID) {
        return prescriptionsRepository.findAllPrescriptionsByDoctorId(doctorID);
    }
}
