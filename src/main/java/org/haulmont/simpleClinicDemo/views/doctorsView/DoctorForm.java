package org.haulmont.simpleClinicDemo.views.doctorsView;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;

@SpringView(name = "doctorsForm")
public class DoctorForm extends Window implements View {

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField middleName = new TextField("Middle name");
    private final TextField specialization = new TextField("Specialization");
    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");
    private Doctor doctor;
    private DoctorsRepository repository;

    private final Binder<Doctor> binder = new Binder<>(Doctor.class);

    public DoctorForm() {
    }

    public DoctorForm(Doctor doctor, DoctorsRepository repository) {
        this.repository = repository;
        this.doctor = doctor;
        setCaption("Input Doctor details");
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

    private void save() {
        Notification notification = new Notification("All these fields are required", Notification.Type.ERROR_MESSAGE);
        notification.setPosition(Position.TOP_CENTER);
        notification.setDelayMsec(1500);
        if (firstName.getValue().trim().isEmpty()|| lastName.getValue().trim().isEmpty()
                || specialization.getValue().trim().isEmpty())
            notification.show(getUI().getPage());
        else {
            try {
                repository.save(doctor);
                getUI().removeWindow(DoctorsView.doctorsForm);
                DoctorsView.updateDoctorsGrid(repository);
            } catch (Exception e) {
                notification.show(getUI().getPage());
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
