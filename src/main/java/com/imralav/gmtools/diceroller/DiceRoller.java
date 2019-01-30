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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiceRoller extends BorderPane {

    private static final String VIEW_PATH = "diceroller/diceroller.fxml";

    private int rollNumber = 1;
    private Font defaultFont;

    @FXML
    private TextArea rollsLog;

    @FXML
    private Label resultLabel;

    @FXML
    private Spinner<Integer> rollsCounter;

    public DiceRoller() throws IOException {
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
        int[] rolls = new int[rollsCount];
        for(int i = 0; i < rollsCount; i++) {
            rolls[i] = ThreadLocalRandom.current().nextInt(size) + 1;
        }
        String resultText = prepareResultText(rolls);
        rollsLog.insertText(0, String.format("Roll #%d: %s%n", rollNumber++, resultText));
        updateResultLabel(resultText);
    }

    private String prepareResultText(int[] rolls) {
        if(rolls.length == 1) {
            return String.valueOf(rolls[0]);
        }
        String rollsText = Arrays.stream(rolls).mapToObj(String::valueOf).collect(Collectors.joining(" + "));
        int sum = Arrays.stream(rolls).sum();
        return String.format("(%s) = %d", rollsText, sum);
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
