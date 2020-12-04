package org.haulmont.simpleClinicDemo.views;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import org.haulmont.simpleClinicDemo.views.templates.CustomHeaderMenu;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MainView extends UI implements ViewDisplay {

    private VerticalLayout panel;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setMargin(false);
        CustomHeaderMenu customHeaderMenu = new CustomHeaderMenu();
        customHeaderMenu.setWidth("100%");
        root.addComponent(customHeaderMenu);

        panel = new VerticalLayout();
        panel.setMargin(false);
        root.addComponent(panel);

        setContent(root);
    }

    @Override
    public void showView(View view) {
        panel.removeAllComponents();
        panel.addComponent((Component) view);
    }
}
