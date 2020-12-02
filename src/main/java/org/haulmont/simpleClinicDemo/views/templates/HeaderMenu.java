package org.haulmont.simpleClinicDemo.views.templates;

import com.vaadin.server.ExternalResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class HeaderMenu extends HorizontalLayout {

    public HeaderMenu() {
        addComponent(navigationBar());
    }

    private HorizontalLayout navigationBar() {
        HorizontalLayout navigationBar = new HorizontalLayout();
        navigationBar.setWidth("100%");
        navigationBar.setPrimaryStyleName(ValoTheme.MENU_ROOT);
        navigationBar.setHeight("50px");

        HorizontalLayout navigationItems = new HorizontalLayout();
        navigationItems.setSpacing(true);
        navigationItems.setHeight("100%");
        navigationItems.addComponent(createNavigationButton("Doctors", "doctors"));
        navigationItems.addComponent(createNavigationButton("Patients", "patients"));
        navigationItems.addComponent(createNavigationButton("Prescriptions", "prescriptions"));
        navigationItems.addComponent(createNavigationButton("About", "about"));

        navigationBar.addComponent(navigationItems);
        return navigationBar;
    }

    private Component createNavigationButton(String caption, String viewName) {
        Link link = new Link(caption, new ExternalResource("#!"+viewName));
        link.addStyleName(ValoTheme.LABEL_SUCCESS);
        HorizontalLayout buttonDiv = new HorizontalLayout(link);
        buttonDiv.setHeight("100%");
        buttonDiv.setMargin(new MarginInfo(false, true));
        buttonDiv.setComponentAlignment(link, Alignment.MIDDLE_CENTER);
        return buttonDiv;
    }
}
