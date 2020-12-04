package org.haulmont.simpleClinicDemo.backend.dao.repository;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrescriptionsRepository extends JpaRepository<Prescription, Long> {

    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :doctorID")
    List<Prescription> findAllPrescriptionsByDoctorId(
            @Param("doctorID") Long doctorID);
}
