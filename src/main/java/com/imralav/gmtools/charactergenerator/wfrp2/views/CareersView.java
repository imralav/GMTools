package com.imralav.gmtools.charactergenerator.wfrp2.views;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Career;
import com.imralav.gmtools.utils.ViewsLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
public class CareersView extends VBox {
    private static final String VIEW_PATH = "characterGenerator/careers/careers.fxml";

    @FXML
    private ListView<Career> careerListView;

    @FXML
    private TextField careersSearch;

    @Autowired
    private CareersView() throws IOException {
        setupView();
    }

    private void setupView() throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public void filterCareers() {
        log.info("Looking for careers with value {}", careersSearch.getText());
    }
}
