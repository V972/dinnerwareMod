package net.v972.dinnerware.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class DinnerwareConfig {
    private static DinnerwareConfigBackend backend;

    private DinnerwareConfig() {
    }

    public static void setBackend(DinnerwareConfigBackend backend) {
        DinnerwareConfig.backend = backend;
    }

    private static DinnerwareConfigBackend backend() {
        if (backend == null) {
            throw new IllegalStateException("Dinnerware config backend has not been initialized");
        }

        return backend;
    }

    // ---------------------------------------------------------------------
    // Common / gameplay config
    // ---------------------------------------------------------------------

    public static int maxPlateStackSize() {
        return backend().maxPlateStackSize();
    }

    public static boolean allowOvereating() {
        return backend().allowOvereating();
    }

    public static boolean onlyFoodOnPlate() {
        return backend().onlyFoodOnPlate();
    }

    public static boolean fragilePlates() {
        return backend().fragilePlates();
    }

    public static boolean rightToLeft() {
        return backend().rightToLeft();
    }

    public static DinnerwareEatingMode eatingMode() {
        return backend().eatingMode();
    }

    public static boolean trayMergeMatchingItem() {
        return backend().trayMergeMatchingItem();
    }

    public static boolean dispensersPlacePlates() {
        return backend().dispensersPlacePlates();
    }

    public static boolean isInFoodBlacklist(Item item) {
        return backend().isInFoodBlacklist(item);
    }

    public static boolean isInFoodBlacklist(ItemStack stack) {
        return backend().isInFoodBlacklist(stack);
    }

    // ---------------------------------------------------------------------
    // Client config
    // ---------------------------------------------------------------------

    public static int maxTrayTooltipLines() {
        return backend().maxTrayTooltipLines();
    }

    public static boolean trayDynamicPlateOffset() {
        return backend().trayDynamicPlateOffset();
    }

    public static int trayGuiX() {
        return backend().trayGuiX();
    }

    public static int trayGuiY() {
        return backend().trayGuiY();
    }
}