package net.v972.dinnerware.config;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface DinnerwareConfigBackend {
    int maxPlateStackSize();
    boolean allowOvereating();
    boolean onlyFoodOnPlate();
    boolean fragilePlates();
    boolean rightToLeft();
    DinnerwareEatingMode eatingMode();
    boolean trayMergeMatchingItem();
    boolean dispensersPlacePlates();
    boolean isInFoodBlacklist(Item item);
    default boolean isInFoodBlacklist(ItemStack stack) {
        return isInFoodBlacklist(stack.getItem());
    }

    int maxTrayTooltipLines();
    boolean trayDynamicPlateOffset();
    boolean showEatingParticles();
    int trayGuiX();
    int trayGuiY();
}