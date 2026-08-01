package net.v972.dinnerware.fabric.config;

import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry.Translatable;
import net.v972.dinnerware.config.DinnerwareEatingMode;

public enum FabricEatingMode implements Translatable {
    QUEUE(DinnerwareEatingMode.QUEUE),
    ROUND_ROBIN(DinnerwareEatingMode.ROUND_ROBIN),
    AIMING(DinnerwareEatingMode.AIMING);

    private final DinnerwareEatingMode commonValue;

    FabricEatingMode(DinnerwareEatingMode commonValue) {
        this.commonValue = commonValue;
    }

    public DinnerwareEatingMode commonValue() {
        return commonValue;
    }

    @Override
    public String getKey() {
        return "config.dinnerware.eating_mode." + name().toLowerCase();
    }
}