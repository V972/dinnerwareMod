package net.v972.dinnerware.forge.config;

import net.minecraft.world.item.Item;
import net.v972.dinnerware.config.DinnerwareConfigBackend;
import net.v972.dinnerware.config.DinnerwareEatingMode;

public final class ForgeDinnerwareConfigBackend implements DinnerwareConfigBackend {
    public static final ForgeDinnerwareConfigBackend INSTANCE = new ForgeDinnerwareConfigBackend();

    private ForgeDinnerwareConfigBackend() {
    }

    @Override
    public int maxPlateStackSize() {
        return ForgeCommonConfig.MAX_PLATE_STACK_SIZE.get();
    }

    @Override
    public boolean allowOvereating() {
        return ForgeCommonConfig.ALLOW_OVEREATING.get();
    }

    @Override
    public boolean onlyFoodOnPlate() {
        return ForgeCommonConfig.ONLY_FOOD_ON_PLATE.get();
    }

    @Override
    public boolean fragilePlates() {
        return ForgeCommonConfig.FRAGILE_PLATES.get();
    }

    @Override
    public boolean rightToLeft() {
        return ForgeCommonConfig.RIGHT_TO_LEFT.get();
    }

    @Override
    public DinnerwareEatingMode eatingMode() {
        return ForgeCommonConfig.EATING_MODE.get();
    }

    @Override
    public boolean trayMergeMatchingItem() {
        return ForgeCommonConfig.TRAY_MERGE_MATCHING_ITEM.get();
    }

    @Override
    public boolean dispensersPlacePlates() {
        return ForgeCommonConfig.DISPENSERS_PLACE_PLATES.get();
    }

    @Override
    public boolean isInFoodBlacklist(Item item) {
        return ForgeCommonConfig.isInFoodBlacklist(item);
    }

    @Override
    public int maxTrayTooltipLines() {
        return ForgeClientConfig.MAX_TRAY_TOOLTIP_LINES.get();
    }

    @Override
    public boolean trayDynamicPlateOffset() {
        return ForgeClientConfig.TRAY_DYNAMIC_PLATE_OFFSET.get();
    }

    public boolean showEatingParticles() {
        return ForgeClientConfig.SHOW_EATING_PARTICLES.get();
    }

    @Override
    public int trayGuiX() {
        return 0; // return ForgeClientConfig.TRAY_GUI_X.get();
    }

    @Override
    public int trayGuiY() {
        return 0; // return ForgeClientConfig.TRAY_GUI_Y.get();
    }
}