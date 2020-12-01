package org.haulmont.simpleClinicDemo.backend.dao.repository;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepository extends JpaRepository<Patient, Long> {
}
