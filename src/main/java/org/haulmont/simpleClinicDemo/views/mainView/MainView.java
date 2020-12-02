package org.haulmont.simpleClinicDemo.views.mainView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.simpleClinicDemo.views.templates.HeaderMenu;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MainView extends UI implements ViewDisplay {

    private VerticalLayout panel;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setMargin(false);
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.setWidth("100%");
        root.addComponent(headerMenu);

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
