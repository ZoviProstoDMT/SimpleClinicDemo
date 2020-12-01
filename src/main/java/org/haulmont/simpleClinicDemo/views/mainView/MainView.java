package org.haulmont.simpleClinicDemo.views.mainView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MainView extends UI implements ViewDisplay {

    private VerticalLayout panel;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setMargin(false);
        root.addComponent(navigationBar());

        panel = new VerticalLayout();
        panel.setMargin(false);
        root.addComponent(panel);

        setContent(root);
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

    private Button createNavigationButton(String caption, String viewName) {
        Button button = new Button(caption);
        button.setHeight("100%");
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener(
                event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        panel.removeAllComponents();
        panel.addComponent((Component) view);
    }
}
