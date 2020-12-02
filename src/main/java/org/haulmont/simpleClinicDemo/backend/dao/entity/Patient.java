package org.haulmont.simpleClinicDemo.backend.dao.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Patient extends AbstractEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String middleName;
    private String phone;

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
}
