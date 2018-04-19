package com.imralav.gmtools.diceroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRoller extends BorderPane {

    private static final String VIEW_PATH = "views/diceroller/diceroller.fxml";

    private int rollNumber = 1;

    @FXML
    private TextArea rollsLog;

    @FXML
    private Label resultLabel;

    public DiceRoller() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(VIEW_PATH));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void rollD4() {
        rollDice(4);
    }

    private void rollDice(Integer size) {
        int rolledValue = ThreadLocalRandom.current().nextInt(size) + 1;
        rollsLog.insertText(0, String.format("Roll #%d: %d\n", rollNumber++, rolledValue));
        resultLabel.setText(String.valueOf(rolledValue));
    }

    @FXML
    private void rollD6() {
        rollDice(6);
    }

    @FXML
    private void rollD8() {
        rollDice(8);
    }

    @FXML
    private void rollD10() {
        rollDice(10);
    }

    @FXML
    private void rollD16() {
        rollDice(16);
    }

    @FXML
    private void rollD20() {
        rollDice(20);
    }

    @FXML
    private void rollD100() {
        rollDice(100);
    }
}
