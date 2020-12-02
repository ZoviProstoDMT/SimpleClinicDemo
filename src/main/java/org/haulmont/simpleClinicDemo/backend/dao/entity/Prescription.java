package org.haulmont.simpleClinicDemo.backend.dao.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Prescription extends AbstractEntity {

    @NotNull
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private int duration;

    @NotNull
    private String priority;

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
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
}
