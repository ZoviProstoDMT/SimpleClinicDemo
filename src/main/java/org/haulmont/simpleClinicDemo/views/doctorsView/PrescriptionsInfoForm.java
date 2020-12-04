package org.haulmont.simpleClinicDemo.views.doctorsView;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.backend.dao.entity.Doctor;
import org.haulmont.simpleClinicDemo.backend.service.DoctorsService;

public class PrescriptionsInfoForm extends Window implements View {

    private final Button okButton = new Button("OK");
    private final DoctorsService doctorsService;

    public PrescriptionsInfoForm(DoctorsService doctorsService) {
        this.doctorsService = doctorsService;
        setCaption(" Prescriptions info");
        setIcon(VaadinIcons.PILLS);
        setModal(true);
        center();
        setContent(prescriptionForm());
    }

    public Component prescriptionForm() {
        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(okButton);
        VerticalLayout prescriptionsLayout = prescriptionsData();
        form.addComponents(prescriptionsLayout, formButtons);
        form.setComponentAlignment(formButtons, Alignment.MIDDLE_CENTER);

        okButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        okButton.setClickShortcut(KeyCode.ENTER);

        okButton.addClickListener(event -> getUI().removeWindow(DoctorsView.prescriptionsForm));
        return form;
    }

    public VerticalLayout prescriptionsData() {
        VerticalLayout layout = new VerticalLayout();
        for (Doctor d : doctorsService.getAllDoctors()) {
            int countOfPrescriptions = doctorsService.getPrescriptionsCount(d);
            if (countOfPrescriptions != 0) {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setWidth("100%");
                horizontalLayout.setSpacing(true);
                HorizontalLayout prescriptionsCountColumn;
                HorizontalLayout doctorsColumn = new HorizontalLayout(new Label(d.toString()));
                doctorsColumn.setMargin(new MarginInfo(false, true, false, false));
                prescriptionsCountColumn = new HorizontalLayout(new Label("Выписано рецептов: " + countOfPrescriptions));
                horizontalLayout.addComponents(doctorsColumn, prescriptionsCountColumn);
                horizontalLayout.setComponentAlignment(doctorsColumn, Alignment.MIDDLE_LEFT);
                horizontalLayout.setComponentAlignment(prescriptionsCountColumn, Alignment.MIDDLE_RIGHT);
                layout.addComponent(horizontalLayout);
            }
        }
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
