package org.haulmont.simpleClinicDemo.views.aboutView;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@ViewScope
@SpringView(name = "")
public class AboutView extends VerticalLayout implements View {

    @PostConstruct
    void init() {
        VerticalLayout layout = new VerticalLayout();
        layout.setHeight("100%");
        layout.addComponent(new Label("This is the test-task application"));
        addComponent(layout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}
