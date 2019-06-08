package com.imralav.gmtools.diceroller;

import com.imralav.gmtools.utils.ViewsLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class DiceRollerView extends BorderPane {

    private static final String VIEW_PATH = "diceroller/diceroller.fxml";

    private DiceRoller diceRoller;
    private int rollNumber = 1;
    private Font defaultFont;

    @FXML
    private TextArea rollsLog;

    @FXML
    private Label resultLabel;

    @FXML
    private Spinner<Integer> rollsCounter;

    @Autowired
    public DiceRollerView(DiceRoller diceRoller) throws IOException {
        this.diceRoller = diceRoller;
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        rollsCounter.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        defaultFont = resultLabel.getFont();
        this.sceneProperty().addListener(scene -> {
            this.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
                updateResultLabelFontSize(resultLabel.getText());
            });
        });
    }

    @FXML
    private void rollD4() {
        rollDice(4);
    }

    private void rollDice(Integer size) {
        int rollsCount = rollsCounter.getValue();
        String resultText = diceRoller.rollDice(size, rollsCount);
        updateRollsLog(resultText);
        updateResultLabel(resultText);
    }

    private void updateRollsLog(String resultText) {
        rollsLog.insertText(0, String.format("#%d: %s%n", rollNumber++, resultText));
    }

    private void updateResultLabel(String resultText) {
        resultLabel.setText(resultText);
        updateResultLabelFontSize(resultText);
    }

    private void updateResultLabelFontSize(String resultText) {
        double availableWidth = resultLabel.getParent().getBoundsInLocal().getWidth();
        double correctFontSize = findFontSizeThatCanFit(defaultFont, resultText, availableWidth);
        resultLabel.setStyle(String.format("-fx-font-size: %s;", correctFontSize));
    }

    private double findFontSizeThatCanFit(Font font, String s, double maxWidth) {
        double fontSize = font.getSize();
        double width = textWidth(font, s);
        if (width > maxWidth) {
            return Math.floor(fontSize * maxWidth / width);
        }
        return Math.floor(fontSize);
    }

    private double textWidth(Font font, String s) {
        Text text = new Text(s);
        text.setFont(font);
        return text.getBoundsInLocal().getWidth();
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
