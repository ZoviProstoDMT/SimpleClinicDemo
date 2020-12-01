package org.haulmont.simpleClinicDemo.views.doctorsView;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.dao.repository.DoctorsRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "doctorsForm")
public class DoctorForm extends Window implements View {

    @Autowired
    DoctorsRepository repository;

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField middleName = new TextField("Middle name");
    private final TextField specialization = new TextField("Specialization");
    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");
    private Doctor doctor;
    Window subWindow;

    private final Binder<Doctor> binder = new Binder<>(Doctor.class);

    public DoctorForm() {
    }

    public DoctorForm(Doctor doctor) {
        center();
        this.doctor = doctor;
        setContent(doctorsForm());
    }

    public Component doctorsForm() {
        setDoctorFields(doctor);
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        verticalLayout.addComponents(firstName, lastName, middleName, specialization, buttons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> this.save());
        cancel.addClickListener(event -> subWindow.close());
        return verticalLayout;
    }

    public void setDoctorFields(Doctor doctor) {
        binder.setBean(doctor);
    }

    private void save() {
        repository.save(doctor);
        subWindow.close();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
