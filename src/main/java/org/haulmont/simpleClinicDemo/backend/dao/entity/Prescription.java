package org.haulmont.simpleClinicDemo.backend.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
}
