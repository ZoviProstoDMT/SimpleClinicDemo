package org.haulmont.simpleClinicDemo.backend.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PATIENTS")
public class Patient extends AbstractEntity {

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstName;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "MIDDLENAME")
    private String middleName;

    @Column(name = "PHONE")
    private String phone;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String middleName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}
