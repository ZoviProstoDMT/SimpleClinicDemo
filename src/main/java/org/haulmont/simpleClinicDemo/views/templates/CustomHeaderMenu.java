package org.haulmont.simpleClinicDemo.views.templates;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.swing.*;
import java.io.File;

public class CustomHeaderMenu extends HorizontalLayout {

    public CustomHeaderMenu() {
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
        navigationItems.addComponent(createNavigationLogoButton());
        navigationItems.addComponent(createNavigationButton("Doctors", "doctors"));
        navigationItems.addComponent(createNavigationButton("Patients", "patients"));
        navigationItems.addComponent(createNavigationButton("Prescriptions", "prescriptions"));

        navigationBar.addComponent(navigationItems);
        return navigationBar;
    }

    private Component createNavigationButton(String caption, String viewName) {
        Link link = new Link(caption, new ExternalResource("#!"+viewName));
        link.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        HorizontalLayout buttonDiv = new HorizontalLayout(link);
        buttonDiv.setHeight("100%");
        buttonDiv.setMargin(new MarginInfo(false, true));
        buttonDiv.setComponentAlignment(link, Alignment.MIDDLE_CENTER);
        return buttonDiv;
    }

    private Component createNavigationLogoButton() {
        FileResource resource = new FileResource(new File("src/main/resources/staticImages/logo200x100.png"));
        Image logo = new Image(null, resource);
        logo.setHeight("100%");
        HorizontalLayout logoLayout = new HorizontalLayout(logo);
        logoLayout.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoLayout.setHeight("100%");
        logoLayout.setMargin(new MarginInfo(false, true));
        logo.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        logoLayout.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        logo.addClickListener(event -> { UI.getCurrent().getNavigator().navigateTo(""); });

        return logoLayout;
    }
}
