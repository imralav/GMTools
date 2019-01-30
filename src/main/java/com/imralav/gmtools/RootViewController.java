
package com.imralav.gmtools;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class RootViewController {
    @FXML
    private TabPane contentTabs;

    @FXML
    public void initialize() {
        contentTabs.getSelectionModel().selectNext();
    }
}
