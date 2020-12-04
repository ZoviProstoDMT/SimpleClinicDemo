package org.haulmont.simpleClinicDemo.views.doctorsView;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.service.DoctorsService;

import java.util.regex.Pattern;

public class DoctorForm extends Window implements View {

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField middleName = new TextField("Middle name");
    private final TextField specialization = new TextField("Specialization");
    private final Button save = new Button("Save", VaadinIcons.CHECK);
    private final Button cancel = new Button("Cancel");
    private final Doctor doctor;
    private final DoctorsService doctorsService;

    private final Binder<Doctor> binder = new Binder<>(Doctor.class);

    public DoctorForm(Doctor doctor, DoctorsService doctorsService) {
        this.doctorsService = doctorsService;
        this.doctor = doctor;
        setCaption(" Input Doctor details");
        setIcon(VaadinIcons.DOCTOR);
        setModal(true);
        center();
        setContent(doctorsForm());
    }

    public Component doctorsForm() {
        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        HorizontalLayout formInputs1 = new HorizontalLayout(firstName, lastName);
        HorizontalLayout formInputs2 = new HorizontalLayout(middleName, specialization);
        form.addComponents(formInputs1, formInputs2, formButtons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);
        specialization.setRequiredIndicatorVisible(true);

        binder.setBean(doctor);
        binder.bindInstanceFields(this);

        save.addClickListener(event -> this.save());
        cancel.addClickListener(event -> getUI().removeWindow(DoctorsView.doctorsForm));
        return form;
    }

    private boolean isInputDataValid() {
        Pattern notOnlyDigits = Pattern.compile("[a-zA-Z,а-яА-Я]+");
        Notification notification = new Notification("Please fill all required fields", Notification.Type.ERROR_MESSAGE);
        notification.setPosition(Position.TOP_CENTER);
        notification.setDelayMsec(1500);
        if (firstName.getValue().trim().isEmpty()|| lastName.getValue().trim().isEmpty()
                || specialization.getValue().trim().isEmpty()) {
            notification.show(getUI().getPage());
            return false;
        }
        else if (!notOnlyDigits.matcher(firstName.getValue()).find()) {
            notification.setCaption("Invalid firstname. Must contain characters");
            notification.show(getUI().getPage());
            return false;
        }
        else if (!notOnlyDigits.matcher(lastName.getValue()).find()) {
            notification.setCaption("Invalid lastname. Must contain characters");
            notification.show(getUI().getPage());
            return false;
        }
        else if (!notOnlyDigits.matcher(specialization.getValue()).find()) {
            notification.setCaption("Invalid specialization. Must contain characters");
            notification.show(getUI().getPage());
            return false;
        }
        if (middleName.getValue().equals("")) {
            return true;
        }
        else {
            if (!notOnlyDigits.matcher(middleName.getValue()).find()) {
                notification.setCaption("Invalid middlename. Must contain characters");
                notification.show(getUI().getPage());
                return false;
            }
        }
        return true;
    }

    private void save() {
        if (isInputDataValid()) {
            try {
                doctorsService.save(doctor);
                getUI().removeWindow(DoctorsView.doctorsForm);
                DoctorsView.updateDoctorsGrid(doctorsService);
            } catch (Exception e) {
                new Notification("Saving error", Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
