package org.haulmont.simpleClinicDemo.views.aboutView;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

@ViewScope
@SpringView(name = "")
public class AboutView extends VerticalLayout implements View {

    @PostConstruct
    void init() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        FileResource resource = new FileResource(new File("src/main/resources/staticImages/about.png"));
        Image image = new Image("", resource);
        image.setWidth("75%");
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(image);
        addComponent(layout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}
