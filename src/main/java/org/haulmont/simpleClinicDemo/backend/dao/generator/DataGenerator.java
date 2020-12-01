package org.haulmont.simpleClinicDemo.backend.dao.generator;

import com.vaadin.spring.annotation.SpringComponent;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(DoctorsRepository doctorsRepository) {
        return args -> {
            Doctor doctor = new Doctor();
            doctor.setFirstName("Ivan");
            doctor.setLastName("Ivanov");
            doctor.setMiddleName("Ivanovich");
            doctor.setSpecialization("Therapist");
            doctorsRepository.save(doctor);
        };
    }
}