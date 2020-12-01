package org.haulmont.simpleClinicDemo.backend.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Patient extends AbstractEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String middleName;
    private String phone;
}
