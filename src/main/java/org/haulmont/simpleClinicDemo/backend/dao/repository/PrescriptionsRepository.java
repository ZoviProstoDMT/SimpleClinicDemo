package org.haulmont.simpleClinicDemo.backend.dao.repository;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionsRepository extends JpaRepository<Prescription, Long> {
}
