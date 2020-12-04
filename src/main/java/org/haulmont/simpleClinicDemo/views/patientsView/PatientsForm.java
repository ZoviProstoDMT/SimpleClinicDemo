package org.haulmont.simpleClinicDemo.views.patientsView;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Patient;
import org.haulmont.simpleClinicDemo.backend.service.PatientsService;

public class PatientsForm extends Window implements View {

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField middleName = new TextField("Middle name");
    private final TextField phone = new TextField("Phone");
    private final Button save = new Button("Save", VaadinIcons.CHECK);
    private final Button cancel = new Button("Cancel");
    private Patient patient;
    private PatientsService patientsService;

    private final Binder<Patient> binder = new Binder<>(Patient.class);

    public PatientsForm() {
    }

    public PatientsForm(Patient patient, PatientsService patientsService) {
        this.patientsService = patientsService;
        this.patient = patient;
        setCaption(" Input Patient details");
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();
        setContent(patientsForm());
    }

    public Component patientsForm() {
        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        HorizontalLayout formInputs1 = new HorizontalLayout(firstName, lastName);
        HorizontalLayout formInputs2 = new HorizontalLayout(middleName, phone);
        form.addComponents(formInputs1, formInputs2, formButtons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);

        binder.setBean(patient);
        binder.bindInstanceFields(this);

        save.addClickListener(event -> this.save());
        cancel.addClickListener(event -> getUI().removeWindow(PatientsView.patientsForm));
        return form;
    }

    private void save() {
        Notification notification = new Notification("All these fields are required", Notification.Type.ERROR_MESSAGE);
        notification.setPosition(Position.TOP_CENTER);
        notification.setDelayMsec(1500);
        if (firstName.getValue().trim().isEmpty()|| lastName.getValue().trim().isEmpty())
            notification.show(getUI().getPage());
        else if (phone.getValue().trim().length() < 6) {
            notification.setCaption("Invalid phone number");
            notification.show(getUI().getPage());
        }
        else {
            try {
                patientsService.save(patient);
                getUI().removeWindow(PatientsView.patientsForm);
                PatientsView.updatePatientsGrid(patientsService);
            } catch (Exception e) {
                notification.setCaption("Saving error");
                notification.show(getUI().getPage());
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
