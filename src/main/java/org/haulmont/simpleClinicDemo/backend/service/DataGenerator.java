package org.haulmont.simpleClinicDemo.backend.service;

import com.vaadin.spring.annotation.SpringComponent;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Prescription;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.util.List;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(DoctorsService doctorsService, PatientsService patientsService,
                                      PrescriptionsService prescriptionsService) {
        return args -> {
            if (doctorsService.getAllDoctors().size() == 0) {
                Doctor doctor = new Doctor("Иван", "Иванов", "Иванович", "Терапевт");
                doctorsService.save(doctor);
                doctor = new Doctor("Пётр", "Петров", "Петрович", "Офтальмолог");
                doctorsService.save(doctor);
                doctor = new Doctor("Алексей", "Алексеев", "Алексеевич", "Лор");
                doctorsService.save(doctor);
                doctor = new Doctor("Дмитрий", "Дмитриев", "Дмитриевич", "Психотерапевт");
                doctorsService.save(doctor);
                doctor = new Doctor("Андрей", "Андреев", "Андреевич", "Терапевт");
                doctorsService.save(doctor);
                doctor = new Doctor("Георгий", "Георгиев", "Георгиевич", "Хирург");
                doctorsService.save(doctor);
                doctor = new Doctor("Леонид", "Леонидов", "Леонидович", "Лор");
                doctorsService.save(doctor);
            }
            if (patientsService.getAllPatients().size() == 0) {
                Patient patient = new Patient("Евгений", "Евгеньев", "Евгеньевич", "+79999999999");
                patientsService.save(patient);
                patient = new Patient("Пётр", "Петров", "Петрович", "+78888888888");
                patientsService.save(patient);
                patient = new Patient("Павел", "Павлов", "Павлович", "+7777777777");
                patientsService.save(patient);
                patient = new Patient("Иван", "Иванов", "Иванович", "+76666666666");
                patientsService.save(patient);
                patient = new Patient("Максим", "Максимов", "Максимович", "+75555555555");
                patientsService.save(patient);
                patient = new Patient("Вячеслав", "Вячеславов", "Вячеславович", "+74444444444");
                patientsService.save(patient);
            }
            if (prescriptionsService.getAllPrescriptions().size() == 0) {
                List<Doctor> doctorList = doctorsService.getAllDoctors();
                List<Patient> patientList = patientsService.getAllPatients();
                Prescription prescription = new Prescription("Нормальный рецепт", doctorList.get(0), patientList.get(0),
                        Date.valueOf("2020-12-05"), 30, "Нормальный");
                prescriptionsService.save(prescription);
                prescription = new Prescription("Нормальный рецепт", doctorList.get(0), patientList.get(3),
                        Date.valueOf("2020-12-05"), 30, "Нормальный");
                prescriptionsService.save(prescription);
                prescription = new Prescription("Срочный рецепт", doctorList.get(1), patientList.get(1),
                        Date.valueOf("2020-12-05"), 1, "Срочный");
                prescriptionsService.save(prescription);
                prescription = new Prescription("Неотложный рецепт", doctorList.get(2), patientList.get(2),
                        Date.valueOf("2020-12-05"), 7, "Неотложный");
                prescriptionsService.save(prescription);
                prescription = new Prescription("Срочный рецепт", doctorList.get(3), patientList.get(4),
                        Date.valueOf("2020-12-05"), 30, "Срочный");
                prescriptionsService.save(prescription);
                prescription = new Prescription("Нормальный рецепт", doctorList.get(3), patientList.get(5),
                        Date.valueOf("2020-12-05"), 30, "Нормальный");
                prescriptionsService.save(prescription);
            }
        };
    }
}
