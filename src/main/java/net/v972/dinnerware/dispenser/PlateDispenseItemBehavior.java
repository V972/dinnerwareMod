package net.v972.dinnerware.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.config.DinnerwareConfig;
import org.jetbrains.annotations.NotNull;

public final class PlateDispenseItemBehavior extends ShulkerBoxDispenseBehavior {
    private static final DefaultDispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR =
            new DefaultDispenseItemBehavior();

    @Override
    protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
        if (!DinnerwareConfig.dispensersPlacePlates()) {
            return dispenseFallback(source, stack);
        }

        ItemStack result = super.execute(source, stack);

        if (!this.isSuccess()) {
            return dispenseFallback(source, stack);
        }

        return result;
    }

    private ItemStack dispenseFallback(BlockSource source, ItemStack stack) {
        this.setSuccess(false);
        return DEFAULT_DISPENSE_BEHAVIOR.dispense(source, stack);
    }
}