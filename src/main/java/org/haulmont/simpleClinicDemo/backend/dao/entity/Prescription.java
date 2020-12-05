package org.haulmont.simpleClinicDemo.backend.dao.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "PRESCRIPTIONS")
public class Prescription extends AbstractEntity {

    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCTORID")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PATIENTID")
    private Patient patient;

    @NotNull
    private Date startDate;

    @NotNull
    private int duration;

    @NotNull
    private String priority;

    public Prescription() {
    }

    public Prescription(String description, Doctor doctor, Patient patient, Date startDate, int duration, String priority) {
        this.description = description;
        this.doctor = doctor;
        this.patient = patient;
        this.startDate = startDate;
        this.duration = duration;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return priority + " рецепт от " + doctor + " для " + patient;
    }
}
