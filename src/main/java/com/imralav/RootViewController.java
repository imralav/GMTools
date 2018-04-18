/*
 * Copyright (c) 2011, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.imralav;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

import java.util.concurrent.ThreadLocalRandom;

public class RootViewController {
    @FXML
    private Label resultLabel;

    @FXML
    private Spinner customDiceSize;

    @FXML
    private void rollD4() {
        setRandomDiceResultForSize(4);
    }

    private void setRandomDiceResultForSize(Integer size) {
        resultLabel.setText(String.valueOf(ThreadLocalRandom.current().nextInt(size)+1));
    }

    @FXML
    private void rollD6() {
        setRandomDiceResultForSize(6);
    }

    @FXML
    private void rollD8() {
        setRandomDiceResultForSize(8);
    }

    @FXML
    private void rollD10() {
        setRandomDiceResultForSize(10);
    }

    @FXML
    private void rollD20() {
        setRandomDiceResultForSize(20);
    }

    @FXML
    private void rollCustom() {
        IntegerSpinnerValueFactory valueFactory = (IntegerSpinnerValueFactory) customDiceSize.getValueFactory();
        setRandomDiceResultForSize(valueFactory.getValue());
    }
}
