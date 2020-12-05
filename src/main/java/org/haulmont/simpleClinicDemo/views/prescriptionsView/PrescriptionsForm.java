package org.haulmont.simpleClinicDemo.views.prescriptionsView;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Prescription;
import org.haulmont.simpleClinicDemo.backend.service.PrescriptionsService;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class PrescriptionsForm extends Window implements View {

    private final Prescription prescription;
    private final PrescriptionsService prescriptionsService;
    private final List<Doctor> allDoctors;
    private final List<Patient> allPatients;
    private final TextArea description = new TextArea("Description");
    private NativeSelect<Doctor> doctor;
    private NativeSelect<Patient> patient;
    private NativeSelect<String> priority;
    private final DateTimeField startDate = new DateTimeField("Start time");
    private NativeSelect<Integer> duration;
    private final Button save = new Button("Save", VaadinIcons.CHECK);
    private final Button cancel = new Button("Cancel");

    public PrescriptionsForm(Prescription prescription, PrescriptionsService prescriptionsService) {
        this.prescriptionsService = prescriptionsService;
        this.prescription = prescription;
        allDoctors = prescriptionsService.getAllDoctors();
        allPatients = prescriptionsService.getAllPatients();
        setCaption(" Input prescription details");
        setIcon(VaadinIcons.PILLS);
        setModal(true);
        center();
        setContent(prescriptionsForm());
    }

    private void fillNativeSelectComponents() {
        doctor = new NativeSelect<>(" Doctor", allDoctors);
        doctor.setEmptySelectionAllowed(false);
        doctor.setSelectedItem(allDoctors.get(0));
        doctor.setIcon(VaadinIcons.DOCTOR);
        patient = new NativeSelect<>(" Patient", allPatients);
        patient.setEmptySelectionAllowed(false);
        patient.setSelectedItem(allPatients.get(0));
        patient.setIcon(VaadinIcons.USER);
        priority = new NativeSelect<>("Priority", Arrays.asList("Нормальный", "Срочный", "Немедленный"));
        priority.setEmptySelectionAllowed(false);
        priority.setSelectedItem("Нормальный");
        duration = new NativeSelect<>("Duration (in days)", Arrays.asList(1, 7, 30));
        duration.setEmptySelectionAllowed(false);
        duration.setSelectedItem(1);
    }

    public Component prescriptionsForm() {
        fillNativeSelectComponents();
        bindPrescriptionsFieldsInForm(prescription);

        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        HorizontalLayout formInputs1 = new HorizontalLayout(doctor, patient);
        HorizontalLayout formInputs2 = new HorizontalLayout(priority, startDate, duration);
        HorizontalLayout formInputs3 = new HorizontalLayout(description);
        formInputs3.setWidth("100%");
        description.setWidth("100%");

        form.addComponents(formInputs1, formInputs2, formInputs3, formButtons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);

        description.setRequiredIndicatorVisible(true);
        doctor.setRequiredIndicatorVisible(true);
        patient.setRequiredIndicatorVisible(true);
        priority.setRequiredIndicatorVisible(true);
        startDate.setRequiredIndicatorVisible(true);
        duration.setRequiredIndicatorVisible(true);

        form.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        save.addClickListener(event -> this.save());
        cancel.addClickListener(event -> getUI().removeWindow(PrescriptionsView.prescriptionsForm));
        return form;
    }

    private void bindPrescriptionsFieldsInForm(Prescription prescription) {
        if (prescription.getDoctor() != null)
            doctor.setSelectedItem(prescription.getDoctor());
        if (prescription.getPatient() != null)
            patient.setSelectedItem(prescription.getPatient());
        if (prescription.getDescription() != null)
            description.setValue(prescription.getDescription());
        if (prescription.getPriority() != null)
            priority.setSelectedItem(prescription.getPriority());
        if (prescription.getStartDate() != null)
            startDate.setValue(prescription.getStartDate().toLocalDate().atStartOfDay());
        if (prescription.getDuration() == 1 || prescription.getDuration() == 7 || prescription.getDuration() == 30)
            duration.setSelectedItem(prescription.getDuration());
    }

    private void savePrescriptionFromForm() {
        prescription.setDescription(description.getValue());
        prescription.setDoctor(doctor.getValue());
        prescription.setPatient(patient.getValue());
        prescription.setPriority(priority.getValue());
        prescription.setStartDate(Date.valueOf(startDate.getValue().toLocalDate()));
        prescription.setDuration(duration.getValue());
    }

    private void save() {
        Notification notification = new Notification("Please fill all required fields", Notification.Type.ERROR_MESSAGE);
        notification.setPosition(Position.TOP_CENTER);
        notification.setDelayMsec(1500);
        if (description.getValue().trim().isEmpty()|| doctor.getValue() == null || patient.getValue() == null
                || startDate.getValue() == null || duration.getValue() == null || priority.getValue() == null)
            notification.show(getUI().getPage());
        else {
            try {
                savePrescriptionFromForm();
                prescriptionsService.save(prescription);
                getUI().removeWindow(PrescriptionsView.prescriptionsForm);
                PrescriptionsView.updatePrescriptionsGrid(prescriptionsService);
            } catch (Exception e) {
                e.printStackTrace();
                notification.setCaption("Saving error");
                notification.show(getUI().getPage());
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
