package org.haulmont.simpleClinicDemo.views.patientsView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.haulmont.simpleClinicDemo.backend.service.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "patients")
public class PatientsView extends VerticalLayout implements View {

    @Autowired
    PatientsService patientsService;

    public static Grid<Patient> grid = new Grid<>(Patient.class);
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");
    public static PatientsForm patientsForm;

    @PostConstruct
    void init() {
        addComponents(headerLabel(), gridLayout(patientsService), buttonsLayout());
        createEventHandlers();
    }

    private HorizontalLayout headerLabel() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setHeight("80px");
        Label gridInfoLabel = new Label("Our patients");
        gridInfoLabel.setStyleName(ValoTheme.LABEL_H1);
        gridInfoLabel.setHeight("50px");
        horizontalLayout.addComponent(gridInfoLabel);
        return horizontalLayout;
    }

    private Grid<Patient> gridLayout(PatientsService PatientsService) {
        grid.setColumns("firstName", "lastName", "middleName", "phone");
        grid.setItems(PatientsService.getAllPatients());
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
        buttonLayout.addComponents(addButton, editButton, deleteButton);
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
            patientsForm = new PatientsForm(new Patient(), patientsService);
            getUI().addWindow(patientsForm);
        });
        editButton.addClickListener(e -> {
            patientsForm = new PatientsForm(grid.asSingleSelect().getValue(), patientsService);
            getUI().addWindow(patientsForm);
        });
        deleteButton.addClickListener(e -> {
            Patient d = grid.asSingleSelect().getValue();
            patientsService.delete(d);
            updatePatientsGrid(patientsService);
            Notification notification = new Notification("Patient " + d.getLastName() + " was successfully deleted",
                    Notification.Type.WARNING_MESSAGE);
            notification.setDelayMsec(1500);
            notification.setPosition(Position.BOTTOM_LEFT);
            notification.show(getUI().getPage());
        });
    }

    public static void updatePatientsGrid(PatientsService PatientsService) {
        grid.setItems(PatientsService.getAllPatients());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
