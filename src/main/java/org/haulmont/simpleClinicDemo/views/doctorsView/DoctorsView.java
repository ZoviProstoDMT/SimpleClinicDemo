package org.haulmont.simpleClinicDemo.views.doctorsView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "doctors")
public class DoctorsView extends VerticalLayout implements View {

    private final Grid<Doctor> grid = new Grid<>(Doctor.class);
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");
    private final Button infoButton = new Button("Prescriptions info");

    @Autowired
    DoctorsRepository repository;

    private Grid<Doctor> gridLayout(DoctorsRepository repository) {
        grid.setColumns("firstName", "lastName", "middleName", "specialization");
        grid.setItems(repository.findAll());
        grid.setSizeFull();
        return grid;
    }

    private HorizontalLayout buttonsLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        infoButton.setEnabled(false);
        buttonLayout.addComponents(addButton, editButton, deleteButton, infoButton);
        return buttonLayout;
    }

    private void createEventHandlers() {
        grid.addSelectionListener(valueChangeEvent -> {
            if (!grid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                infoButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                infoButton.setEnabled(false);
            }
        });
        addButton.addClickListener(e -> {
            DoctorForm doctorsForm = new DoctorForm(new Doctor());
            UI.getCurrent().addWindow(doctorsForm);
        });
        editButton.addClickListener(e -> {
            DoctorForm doctorsForm = new DoctorForm(grid.asSingleSelect().getValue());
            UI.getCurrent().addWindow(doctorsForm);
        });
        deleteButton.addClickListener(e -> {
            repository.delete(grid.asSingleSelect().getValue());
            updateDoctorsGrid();
        });
        infoButton.addClickListener(e -> {
            DoctorForm doctorsForm = new DoctorForm(grid.asSingleSelect().getValue());
            UI.getCurrent().addWindow(doctorsForm);
        });
    }

    public void updateDoctorsGrid() {
        grid.setItems(repository.findAll());
    }

    @PostConstruct
    void init() {
        addComponents(gridLayout(repository), buttonsLayout());
        createEventHandlers();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
