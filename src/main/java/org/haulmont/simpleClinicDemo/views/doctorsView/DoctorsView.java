package org.haulmont.simpleClinicDemo.views.doctorsView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "doctors")
public class DoctorsView extends VerticalLayout implements View {

    @Autowired
    DoctorsService doctorsService;

    public static Grid<Doctor> grid = new Grid<>(Doctor.class);
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");
    private final Button infoButton = new Button("Prescriptions info");
    public static DoctorForm doctorsForm;
    public static PrescriptionsInfoForm prescriptionsForm;

    @PostConstruct
    void init() {
        addComponents(headerLabel(), gridLayout(doctorsService), buttonsLayout());
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

    private Grid<Doctor> gridLayout(DoctorsService doctorsService) {
        grid.setColumns("firstName", "lastName", "middleName", "specialization");
        grid.setItems(doctorsService.getAllDoctors());
        grid.setSizeFull();
        return grid;
    }

    private HorizontalLayout buttonsLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("100%");
        HorizontalLayout infoLayout = new HorizontalLayout(infoButton);
        HorizontalLayout activeButtons = new HorizontalLayout(addButton, editButton, deleteButton);
        buttonLayout.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        editButton.setIcon(VaadinIcons.PENCIL);
        deleteButton.setIcon(VaadinIcons.MINUS);
        addButton.setIcon(VaadinIcons.PLUS);
        infoButton.setIcon(VaadinIcons.PILLS);

        buttonLayout.addComponents(activeButtons, infoLayout);
        buttonLayout.setComponentAlignment(infoLayout, Alignment.MIDDLE_RIGHT);
        buttonLayout.setComponentAlignment(activeButtons, Alignment.MIDDLE_LEFT);
        return buttonLayout;
    }

    private void createEventHandlers() {
        grid.addSelectionListener(valueChangeEvent -> {
            if (!grid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });
        addButton.addClickListener(e -> {
            doctorsForm = new DoctorForm(new Doctor(), doctorsService);
            getUI().addWindow(doctorsForm);
        });
        editButton.addClickListener(e -> {
            doctorsForm = new DoctorForm(grid.asSingleSelect().getValue(), doctorsService);
            getUI().addWindow(doctorsForm);
        });
        deleteButton.addClickListener(e -> {
            Doctor d = grid.asSingleSelect().getValue();
            try {
                doctorsService.delete(d);
                updateDoctorsGrid(doctorsService);
                Notification notification = new Notification("Dr. " + d.getLastName() + " was successfully deleted",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.setPosition(Position.BOTTOM_LEFT);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification deleteNotification = new Notification("Невозможно удалить врача, у которого есть рецепты",
                        Notification.Type.WARNING_MESSAGE);
                deleteNotification.show(getUI().getPage());
            }
        });
        infoButton.addClickListener(e -> {
            prescriptionsForm = new PrescriptionsInfoForm(doctorsService);
            getUI().addWindow(prescriptionsForm);
        });
    }

    public static void updateDoctorsGrid(DoctorsService doctorsService) {
        grid.setItems(doctorsService.getAllDoctors());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
