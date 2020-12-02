package org.haulmont.simpleClinicDemo.views.doctorsView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;
import org.haulmont.simpleClinicDemo.backend.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;

@SpringView(name = "doctors")
public class DoctorsView extends VerticalLayout implements View {

    @Autowired
    DoctorsRepository repository;

    @Autowired
    DoctorsService doctorsService;

    public static Grid<Doctor> grid = new Grid<>(Doctor.class);
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");
    private final Button infoButton = new Button("Prescriptions info");
    public static DoctorForm doctorsForm;

    @PostConstruct
    void init() {
        System.out.println("Repository: " + repository + "\n" + repository.findAll());
        System.out.println("Service: " + doctorsService + "\n" + doctorsService.getAllDoctors());
        addComponents(headerLabel(), gridLayout(repository), buttonsLayout());
        createEventHandlers();
    }

    private HorizontalLayout headerLabel() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setHeight("80px");
        Label gridInfoLabel = new Label("Our doctors");
        gridInfoLabel.setStyleName(ValoTheme.LABEL_H1);
        gridInfoLabel.setHeight("50px");
        horizontalLayout.addComponent(gridInfoLabel);
        return horizontalLayout;
    }

    private Grid<Doctor> gridLayout(DoctorsRepository repository) {
        grid.setColumns("firstName", "lastName", "middleName", "specialization");
        grid.setItems((Collection<Doctor>) repository.findAll());
        grid.setSizeFull();
        return grid;
    }

    private HorizontalLayout buttonsLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
        buttonLayout.setSpacing(true);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        infoButton.setEnabled(false);
        buttonLayout.addComponents(addButton, editButton, infoButton, deleteButton);
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
            doctorsForm = new DoctorForm(new Doctor(), repository);
            getUI().addWindow(doctorsForm);
        });
        editButton.addClickListener(e -> {
            doctorsForm = new DoctorForm(grid.asSingleSelect().getValue(), repository);
            getUI().addWindow(doctorsForm);
        });
        deleteButton.addClickListener(e -> {
            Doctor d = grid.asSingleSelect().getValue();
            repository.delete(d);
            updateDoctorsGrid(repository);
            Notification notification = new Notification("Dr. " + d.getLastName() + " was successfully deleted",
                    Notification.Type.WARNING_MESSAGE);
            notification.setDelayMsec(1500);
            notification.setPosition(Position.BOTTOM_LEFT);
            notification.show(getUI().getPage());
        });
        infoButton.addClickListener(e -> {
            doctorsForm = new DoctorForm(grid.asSingleSelect().getValue(), repository);
            getUI().addWindow(doctorsForm);
        });
    }

    public static void updateDoctorsGrid(DoctorsRepository repository) {
        grid.setItems((Collection<Doctor>) repository.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
