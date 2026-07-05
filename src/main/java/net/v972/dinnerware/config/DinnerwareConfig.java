package net.v972.dinnerware.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class DinnerwareConfig {
    private DinnerwareConfig() {
    }

    // ---------------------------------------------------------------------
    // Common / gameplay config
    // ---------------------------------------------------------------------

    public static int maxPlateStackSize() {
        return CommonConfig.MAX_PLATE_STACK_SIZE.get();
    }

    public static boolean allowOvereating() {
        return CommonConfig.ALLOW_OVEREATING.get();
    }

    public static boolean onlyFoodOnPlate() {
        return CommonConfig.ONLY_FOOD_ON_PLATE.get();
    }

    public static boolean fragilePlates() {
        return CommonConfig.FRAGILE_PLATES.get();
    }

    public static boolean rightToLeft() {
        return CommonConfig.RIGHT_TO_LEFT.get();
    }

    public static DinnerwareEatingMode eatingMode() {
        return CommonConfig.EATING_MODE.get();
    }

    public static boolean trayMergeMatchingItem() {
        return CommonConfig.TRAY_MERGE_MATCHING_ITEM.get();
    }

    public static boolean dispensersPlacePlates() {
        return CommonConfig.DISPENSERS_PLACE_PLATES.get();
    }

    public static boolean isInFoodBlacklist(ItemStack stack) {
        return CommonConfig.isInFoodBlacklist(stack);
    }

    public static boolean isInFoodBlacklist(Item item) {
        return CommonConfig.isInFoodBlacklist(item);
    }

    // ---------------------------------------------------------------------
    // Client config
    // ---------------------------------------------------------------------

    public static int maxTrayTooltipLines() {
        return ClientConfig.MAX_TRAY_TOOLTIP_LINES.get();
    }

    public static boolean trayDynamicPlateOffset() {
        return ClientConfig.TRAY_DYNAMIC_PLATE_OFFSET.get();
    }

//    public static int trayGuiXOffset() {
//        return ClientConfig.TRAY_GUI_X.get();
//    }
//
//    public static int trayGuiYOffset() {
//        return ClientConfig.TRAY_GUI_Y.get();
//    }
}