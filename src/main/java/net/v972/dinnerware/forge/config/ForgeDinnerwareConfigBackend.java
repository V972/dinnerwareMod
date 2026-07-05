package net.v972.dinnerware.forge.config;

import net.minecraft.world.item.Item;
import net.v972.dinnerware.config.ClientConfig;
import net.v972.dinnerware.config.CommonConfig;
import net.v972.dinnerware.config.DinnerwareConfigBackend;
import net.v972.dinnerware.config.DinnerwareEatingMode;

public final class ForgeDinnerwareConfigBackend implements DinnerwareConfigBackend {
    public static final ForgeDinnerwareConfigBackend INSTANCE = new ForgeDinnerwareConfigBackend();

    private ForgeDinnerwareConfigBackend() {
    }

    @Override
    public int maxPlateStackSize() {
        return CommonConfig.MAX_PLATE_STACK_SIZE.get();
    }

    @Override
    public boolean allowOvereating() {
        return CommonConfig.ALLOW_OVEREATING.get();
    }

    @Override
    public boolean onlyFoodOnPlate() {
        return CommonConfig.ONLY_FOOD_ON_PLATE.get();
    }

    @Override
    public boolean fragilePlates() {
        return CommonConfig.FRAGILE_PLATES.get();
    }

    @Override
    public boolean rightToLeft() {
        return CommonConfig.RIGHT_TO_LEFT.get();
    }

    @Override
    public DinnerwareEatingMode eatingMode() {
        return CommonConfig.EATING_MODE.get();
    }

    @Override
    public boolean trayMergeMatchingItem() {
        return CommonConfig.TRAY_MERGE_MATCHING_ITEM.get();
    }

    @Override
    public boolean dispensersPlacePlates() {
        return CommonConfig.DISPENSERS_PLACE_PLATES.get();
    }

    @Override
    public boolean isInFoodBlacklist(Item item) {
        return CommonConfig.isInFoodBlacklist(item);
    }

    @Override
    public int maxTrayTooltipLines() {
        return ClientConfig.MAX_TRAY_TOOLTIP_LINES.get();
    }

    @Override
    public boolean trayDynamicPlateOffset() {
        return ClientConfig.TRAY_DYNAMIC_PLATE_OFFSET.get();
    }

    @Override
    public int trayGuiX() {
        return 0; // return ClientConfig.TRAY_GUI_X.get();
    }

    @Override
    public int trayGuiY() {
        return 0; // return ClientConfig.TRAY_GUI_Y.get();
    }
}