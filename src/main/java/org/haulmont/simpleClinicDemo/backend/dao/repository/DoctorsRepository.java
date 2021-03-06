package org.haulmont.simpleClinicDemo.backend.dao.repository;

import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Long> {
}
