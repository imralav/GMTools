package com.imralav.gmtools.charactergenerator.wfrp2.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BindingsTest {
    private static final Logger log = LoggerFactory.getLogger(BindingsTest.class);

    @Test
    public void testujemy() {
        ObservableList<String> nazwy = FXCollections.observableArrayList();
        IntegerBinding integerBinding = Bindings.createIntegerBinding(nazwy::size, nazwy);
        integerBinding.addListener((observable, oldValue, newValue) -> {
            log.info("nazwy.size: {} => {}", oldValue, newValue);
        });
        integerBinding.addListener(observable -> {
            log.info("observable: {}", observable);
        });
        nazwy.addAll("a", "b", "c");
        nazwy.remove("b");
    }
}