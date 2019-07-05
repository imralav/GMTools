package com.imralav.gmtools.charactergenerator.wfrp2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Getter
@NoArgsConstructor
public class Characteristics {
    public static final Characteristics HALFLING = new Characteristics(10, 30, 10, 10, 30, 20, 20, 30);
    public static final Characteristics DWARF = new Characteristics(30, 20, 20, 30, 10, 20, 20, 10);
    public static final Characteristics HUMAN = new Characteristics(20, 20, 20, 20, 20, 20, 20, 20);
    public static final Characteristics ELF = new Characteristics(20, 30, 20, 20, 30, 20, 20, 20);

    public static final List<String> MAIN_CHARACTERISTICS_NAMES = Arrays.asList(
            "weaponSkill",
            "ballisticSkill",
            "strength",
            "toughness",
            "agility",
            "intelligence",
            "willPower",
            "fellowship"
    );

    public static final List<Function<Characteristics, IntegerProperty>> MAIN_CHARACTERISTICS_GETTERS = Arrays.asList(
            Characteristics::getWeaponSkillProperty,
            Characteristics::getBallisticSkillProperty,
            Characteristics::getStrengthProperty,
            Characteristics::getToughnessProperty,
            Characteristics::getAgilityProperty,
            Characteristics::getIntelligenceProperty,
            Characteristics::getWillPowerProperty,
            Characteristics::getFellowshipProperty
    );

    private IntegerProperty weaponSkillProperty = new SimpleIntegerProperty(0);
    private IntegerProperty ballisticSkillProperty = new SimpleIntegerProperty(0);
    private IntegerProperty strengthProperty = new SimpleIntegerProperty(0);
    private IntegerProperty toughnessProperty = new SimpleIntegerProperty(0);
    private IntegerProperty agilityProperty = new SimpleIntegerProperty(0);
    private IntegerProperty intelligenceProperty = new SimpleIntegerProperty(0);
    private IntegerProperty willPowerProperty = new SimpleIntegerProperty(0);
    private IntegerProperty fellowshipProperty = new SimpleIntegerProperty(0);
    private IntegerProperty attacksProperty = new SimpleIntegerProperty(0);
    private IntegerProperty woundsProperty = new SimpleIntegerProperty(0);
    private IntegerProperty strengthBonusProperty = new SimpleIntegerProperty(0);
    private IntegerProperty toughnessBonusProperty = new SimpleIntegerProperty(0);
    private IntegerProperty movementProperty = new SimpleIntegerProperty(0);
    private IntegerProperty magicProperty = new SimpleIntegerProperty(0);
    private IntegerProperty instanityPointsProperty = new SimpleIntegerProperty(0);
    private IntegerProperty fatePointsProperty = new SimpleIntegerProperty(0);

    private Characteristics(int weaponSkill, int ballisticSkill, int strength, int toughness, int agility, int intelligence, int willPower, int fellowship) {
        this.weaponSkillProperty.set(weaponSkill);
        this.ballisticSkillProperty.set(ballisticSkill);
        this.strengthProperty.set(strength);
        this.toughnessProperty.set(toughness);
        this.agilityProperty.set(agility);
        this.intelligenceProperty.set(intelligence);
        this.willPowerProperty.set(willPower);
        this.fellowshipProperty.set(fellowship);
    }

    public Integer getWeaponSkill() {
        return weaponSkillProperty.get();
    }

    public void setWeaponSkill(Integer value) {
        weaponSkillProperty.set(value);
    }

    public Integer getBallisticSkill() {
        return ballisticSkillProperty.get();
    }

    public void setBallisticSkill(Integer value) {
        ballisticSkillProperty.set(value);
    }

    public Integer getStrength() {
        return strengthProperty.get();
    }

    public void setStrength(Integer value) {
        strengthProperty.set(value);
    }

    public Integer getToughness() {
        return toughnessProperty.get();
    }

    public void setToughness(Integer value) {
        toughnessProperty.set(value);
    }

    public Integer getAgility() {
        return agilityProperty.get();
    }

    public void setAgility(Integer value) {
        agilityProperty.set(value);
    }

    public Integer getIntelligence() {
        return intelligenceProperty.get();
    }

    public void setIntelligence(Integer value) {
        intelligenceProperty.set(value);
    }

    public Integer getWillPower() {
        return willPowerProperty.get();
    }

    public void setWilLPower(Integer value) {
        willPowerProperty.set(value);
    }

    public Integer getFellowship() {
        return fellowshipProperty.get();
    }

    public void setFellowship(Integer value) {
        fellowshipProperty.set(value);
    }

    @Override
    public String toString() {
        return "Characteristics{" +
                "weaponSkillProperty=" + weaponSkillProperty.get() +
                ", ballisticSkillProperty=" + ballisticSkillProperty.get() +
                ", strengthProperty=" + strengthProperty.get() +
                ", toughnessProperty=" + toughnessProperty.get() +
                ", agilityProperty=" + agilityProperty.get() +
                ", intelligenceProperty=" + intelligenceProperty.get() +
                ", willPowerProperty=" + willPowerProperty.get() +
                ", fellowshipProperty=" + fellowshipProperty.get() +
                ", attacksProperty=" + attacksProperty.get() +
                ", woundsProperty=" + woundsProperty.get() +
                ", strengthBonusProperty=" + strengthBonusProperty.get() +
                ", toughnessBonusProperty=" + toughnessBonusProperty.get() +
                ", movementProperty=" + movementProperty.get() +
                ", magicProperty=" + magicProperty.get() +
                ", instanityPointsProperty=" + instanityPointsProperty.get() +
                ", fatePointsProperty=" + fatePointsProperty.get() +
                '}';
    }
}
