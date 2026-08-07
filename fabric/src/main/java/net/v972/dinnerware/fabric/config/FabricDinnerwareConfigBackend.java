package net.v972.dinnerware.fabric.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.v972.dinnerware.config.DinnerwareConfigBackend;
import net.v972.dinnerware.config.DinnerwareEatingMode;


public final class FabricDinnerwareConfigBackend
        implements DinnerwareConfigBackend {

    public static final FabricDinnerwareConfigBackend INSTANCE =
            new FabricDinnerwareConfigBackend();

    private FabricDinnerwareConfigBackend() {
    }

    @Override
    public int maxPlateStackSize() {
        return DinnerwareFabricConfigs.common().maxPlateStackSize;
    }

    @Override
    public boolean allowOvereating() {
        return DinnerwareFabricConfigs.common().allowOvereating;
    }

    @Override
    public boolean onlyFoodOnPlate() {
        return DinnerwareFabricConfigs.common().onlyFoodOnPlate;
    }

    @Override
    public boolean fragilePlates() {
        return DinnerwareFabricConfigs.common().fragilePlates;
    }

    @Override
    public boolean rightToLeft() {
        return DinnerwareFabricConfigs.common().rightToLeft;
    }

    @Override
    public DinnerwareEatingMode eatingMode() {
        return DinnerwareFabricConfigs.common().eatingMode.commonValue();
    }

    @Override
    public boolean trayMergeMatchingItem() {
        return DinnerwareFabricConfigs.common().trayMergeMatchingItem;
    }

    @Override
    public boolean dispensersPlacePlates() {
        return DinnerwareFabricConfigs.common().dispensersPlacePlates;
    }

    @Override
    public boolean isInFoodBlacklist(Item item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        if (id == BuiltInRegistries.ITEM.getDefaultKey()) return false;
        String itemId = id.toString();
        return DinnerwareFabricConfigs.common().foodBlacklist.contains(itemId);
    }

    @Override
    public int maxTrayTooltipLines() {
        return DinnerwareFabricConfigs.client().maxTrayTooltipLines;
    }

    @Override
    public boolean trayDynamicPlateOffset() {
        return DinnerwareFabricConfigs.client().trayDynamicPlateOffset;
    }

    public boolean showEatingParticles() {
        return DinnerwareFabricConfigs.client().showEatingParticles;
    }

    @Override
    public int trayGuiX() {
        return 0;
    }

    @Override
    public int trayGuiY() {
        return 0;
    }
}