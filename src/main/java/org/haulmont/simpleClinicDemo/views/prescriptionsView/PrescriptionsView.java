package org.haulmont.simpleClinicDemo.views.prescriptionsView;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Prescription;
import org.haulmont.simpleClinicDemo.backend.service.PrescriptionsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "prescriptions")
public class PrescriptionsView extends VerticalLayout implements View {

    @Autowired
    PrescriptionsService prescriptionsService;

    public static Grid<Prescription> grid = new Grid<>(Prescription.class);
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");
    public static PrescriptionsForm prescriptionsForm;

    @PostConstruct
    void init() {
        addComponents(headerLabel(), filtersLayout(), gridLayout(prescriptionsService), buttonsLayout());
        createEventHandlers();
    }

    private HorizontalLayout headerLabel() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setHeight("75px");
        Label gridInfoLabel = new Label("Medical prescriptions");
        gridInfoLabel.setStyleName(ValoTheme.LABEL_H1);
        gridInfoLabel.setHeight("50px");
        horizontalLayout.addComponent(gridInfoLabel);
        return horizontalLayout;
    }

    private Grid<Prescription> gridLayout(PrescriptionsService prescriptionsService) {
        grid.setColumns("priority", "doctor", "patient", "description", "startDate", "duration");
        grid.setItems(prescriptionsService.getAllPrescriptions());
        grid.setSizeFull();
        return grid;
    }

    private HorizontalLayout filtersLayout() {
        Label label = new Label("Filters: ");
        label.setSizeFull();
        HorizontalLayout labelLayout = new HorizontalLayout();
        labelLayout.setWidth("100%");
        labelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        labelLayout.addComponents(label);
        TextField descriptionFilter = new TextField();
        descriptionFilter.setPlaceholder("Description...");
        TextField priorityFilter = new TextField();
        priorityFilter.setPlaceholder("Priority...");
        TextField patientFilter = new TextField();
        patientFilter.setPlaceholder("Patients name...");
        HorizontalLayout filters = new HorizontalLayout(labelLayout, descriptionFilter, priorityFilter, patientFilter);
        descriptionFilter.addValueChangeListener(this::addDescriptionFilter);
        priorityFilter.addValueChangeListener(this::addPriorityFilter);
        patientFilter.addValueChangeListener(this::addPatientFilter);
        return filters;
    }

    private HorizontalLayout buttonsLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
        editButton.setIcon(VaadinIcons.PENCIL);
        deleteButton.setIcon(VaadinIcons.MINUS);
        addButton.setIcon(VaadinIcons.PLUS);
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
            prescriptionsForm = new PrescriptionsForm(new Prescription(), prescriptionsService);
            getUI().addWindow(prescriptionsForm);
        });
        editButton.addClickListener(e -> {
            prescriptionsForm = new PrescriptionsForm(grid.asSingleSelect().getValue(), prescriptionsService);
            getUI().addWindow(prescriptionsForm);
        });
        deleteButton.addClickListener(e -> {
            Prescription p = grid.asSingleSelect().getValue();

            prescriptionsService.delete(p);
            updatePrescriptionsGrid(prescriptionsService);
            Notification notification = new Notification("Prescription from " + p.getDoctor()
                    + " to " + p.getPatient() + " was successfully deleted",
                    Notification.Type.WARNING_MESSAGE);
            notification.setDelayMsec(1500);
            notification.setPosition(Position.BOTTOM_LEFT);
            notification.show(getUI().getPage());
        });
    }

    private void addDescriptionFilter(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Prescription> dataProvider = (ListDataProvider<Prescription>) grid.getDataProvider();
        dataProvider.setFilter(Prescription::getDescription, description -> isContains(description, event.getValue()));
    }

    private void addPriorityFilter(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Prescription> dataProvider = (ListDataProvider<Prescription>) grid.getDataProvider();
        dataProvider.setFilter(Prescription::getPriority, priority -> isContains(priority, event.getValue()));
    }

    private void addPatientFilter(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Prescription> dataProvider = (ListDataProvider<Prescription>) grid.getDataProvider();
        dataProvider.setFilter(Prescription::getPatient, patient -> isContains(patient.toString(), event.getValue()));
    }


    private Boolean isContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    public static void updatePrescriptionsGrid(PrescriptionsService prescriptionsService) {
        grid.setItems(prescriptionsService.getAllPrescriptions());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
