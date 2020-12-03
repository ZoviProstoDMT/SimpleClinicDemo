package org.haulmont.simpleClinicDemo.views.prescriptionsView;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Prescription;
import org.haulmont.simpleClinicDemo.backend.service.PrescriptionsService;

import java.util.Arrays;
import java.util.List;

public class PrescriptionsForm extends Window implements View {

    private Prescription prescription;
    private PrescriptionsService prescriptionsService;
    private List<Doctor> allDoctors;
    private List<Patient> allPatients;
    private final TextArea description = new TextArea("Description");
    private NativeSelect<Doctor> doctor;
    private NativeSelect<Patient> patient;
    private NativeSelect<String> priority;
    private final DateTimeField startDate = new DateTimeField("Start time");
    private NativeSelect<Integer> duration;
    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");

    public PrescriptionsForm() {
    }

    public PrescriptionsForm(Prescription prescription, PrescriptionsService prescriptionsService) {
        this.prescriptionsService = prescriptionsService;
        this.prescription = prescription;
        allDoctors = prescriptionsService.getAllDoctors();
        allPatients = prescriptionsService.getAllPatients();
        setCaption("Input prescription details");
        setModal(true);
        center();
        setContent(prescriptionsForm());
    }

    private void fillNativeSelectComponents() {
        doctor = new NativeSelect<>("Doctor", allDoctors);
        doctor.setEmptySelectionAllowed(false);
        doctor.setSelectedItem(allDoctors.get(0));
        patient = new NativeSelect<>("Patient", allPatients);
        patient.setEmptySelectionAllowed(false);
        patient.setSelectedItem(allPatients.get(0));
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
        form.addComponents(formInputs1, formInputs2, formInputs3, formButtons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);

        description.setRequiredIndicatorVisible(true);
        doctor.setRequiredIndicatorVisible(true);
        patient.setRequiredIndicatorVisible(true);
        priority.setRequiredIndicatorVisible(true);
        startDate.setRequiredIndicatorVisible(true);
        duration.setRequiredIndicatorVisible(true);

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
            startDate.setValue(prescription.getStartDate());
        if (prescription.getDuration() == 1 || prescription.getDuration() == 7 || prescription.getDuration() == 30)
            duration.setSelectedItem(prescription.getDuration());
    }

    private void savePrescriptionFromForm() {
        prescription.setDescription(description.getValue());
        prescription.setDoctor(doctor.getValue());
        prescription.setPatient(patient.getValue());
        prescription.setPriority(priority.getValue());
        prescription.setStartDate(startDate.getValue());
        prescription.setDuration(duration.getValue());
    }

    private void save() {
        Notification notification = new Notification("All these fields are required", Notification.Type.ERROR_MESSAGE);
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
