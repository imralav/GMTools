package com.imralav.gmtools.gui.charactergenerator.wfrp2.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import lombok.Getter;

import java.util.function.Function;

@Getter
public class CharacterProfile {
    public static final int MAIN_TO_BONUS_CHARACTERISTIC_DIVIDER = 10;
    private Characteristics starting;
    private Characteristics advance;
    private Characteristics advanceSpent;
    private Characteristics current;
    private Characteristics bonus;

    public CharacterProfile() {
        starting = new Characteristics();
        advance = new Characteristics();
        advanceSpent = new Characteristics();
        current = new Characteristics();
        bonus = new Characteristics();
        connectCharacteristics();
    }

    private void connectCharacteristics() {
        bindRegularCharacteristic(Characteristics::getWeaponSkillProperty);
        bindRegularCharacteristic(Characteristics::getBallisticSkillProperty);
        bindRegularCharacteristic(Characteristics::getStrengthProperty);
        bindRegularCharacteristic(Characteristics::getToughnessProperty);
        bindRegularCharacteristic(Characteristics::getAgilityProperty);
        bindRegularCharacteristic(Characteristics::getIntelligenceProperty);
        bindRegularCharacteristic(Characteristics::getWillPowerProperty);
        bindRegularCharacteristic(Characteristics::getFellowshipProperty);
        bindRegularCharacteristic(Characteristics::getAttacksProperty);
        bindRegularCharacteristic(Characteristics::getWoundsProperty);
        bindRegularCharacteristic(Characteristics::getMagicProperty);
        bindBonuses(Characteristics::getStrengthProperty, Characteristics::getStrengthBonusProperty);
        bindBonuses(Characteristics::getToughnessProperty, Characteristics::getToughnessBonusProperty);
    }

    private void bindRegularCharacteristic(Function<Characteristics, IntegerProperty> getValueFunction) {
        NumberBinding startingValueWithCurrentAdvance = Bindings.add(getValueFunction.apply(starting), getValueFunction.apply(advanceSpent));
        getValueFunction.apply(current).bind(Bindings.add(startingValueWithCurrentAdvance, getValueFunction.apply(bonus)));
    }

    private void bindBonuses(Function<Characteristics, IntegerProperty> getMainValueFunction, Function<Characteristics, IntegerProperty> getBonusValueFunction) {
        IntegerProperty bonusValue = getBonusValueFunction.apply(starting);
        bonusValue.bind(Bindings.divide(getMainValueFunction.apply(current), MAIN_TO_BONUS_CHARACTERISTIC_DIVIDER));
        getBonusValueFunction.apply(current).bind(Bindings.add(bonusValue, getBonusValueFunction.apply(bonus)));
    }
}
