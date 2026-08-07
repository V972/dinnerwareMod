package net.v972.dinnerware.item;

import net.minecraft.world.item.Item;

import java.util.Set;
import java.util.function.Supplier;

public final class DinnerwareItems {
    private static Supplier<Item[]> trayItemsArraySupplier;
    private static Supplier<Item[]> survivalPlateItemsArraySupplier;
    private static Supplier<Set<Item>> knownPlateItemsSetSupplier;
    private static Supplier<Set<Item>> trayItemsSetSupplier;

    private DinnerwareItems() {
    }

    public static void setTrayItemsArraySupplier(Supplier<Item[]> supplier) {
        trayItemsArraySupplier = supplier;
    }

    public static void setSurvivalPlateItemsArraySupplier(Supplier<Item[]> supplier) {
        survivalPlateItemsArraySupplier = supplier;
    }

    public static void setKnownPlateItemsSetSupplier(Supplier<Set<Item>> supplier) {
        knownPlateItemsSetSupplier = supplier;
    }

    public static void setTrayItemsSetSupplier(Supplier<Set<Item>> supplier) {
        trayItemsSetSupplier = supplier;
    }

    public static Item[] getTrayItemsArray() {
        if (trayItemsArraySupplier == null) {
            throw new IllegalStateException("Dinnerware items have not been initialized");
        }

        return trayItemsArraySupplier.get();
    }

    public static Item[] getSurvivalPlateItemsArray() {
        if (survivalPlateItemsArraySupplier == null) {
            throw new IllegalStateException("Dinnerware items have not been initialized");
        }

        return survivalPlateItemsArraySupplier.get();
    }

    public static Set<Item> getKnownPlateItemsSet() {
        if (knownPlateItemsSetSupplier == null) {
            throw new IllegalStateException("Dinnerware items have not been initialized");
        }

        return knownPlateItemsSetSupplier.get();
    }

    public static Set<Item> getTrayItemsSet() {
        if (trayItemsSetSupplier == null) {
            throw new IllegalStateException("Dinnerware items have not been initialized");
        }

        return trayItemsSetSupplier.get();
    }
}