package org.haulmont.simpleClinicDemo.backend.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DOCTORS")
public class Doctor extends AbstractEntity {

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstName;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "MIDDLENAME")
    private String middleName;

    @NotNull
    @Column(name = "SPECIALIZATION")
    private String specialization;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Dr." + lastName + " : " + specialization;
    }
}
