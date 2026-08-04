package net.v972.dinnerware.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.config.DinnerwareConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PlateDispenseItemBehavior extends ShulkerBoxDispenseBehavior {
    private final DispenseItemBehavior fallback;
    private boolean delegatedToFallback;

    private static final DefaultDispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR =
        new DefaultDispenseItemBehavior();

    public PlateDispenseItemBehavior() {
        this(null);
    }

    public PlateDispenseItemBehavior(@Nullable DispenseItemBehavior fallback) {
        this.fallback = fallback == null ||
            fallback == DispenseItemBehavior.NOOP ||
            fallback instanceof PlateDispenseItemBehavior
            ? DEFAULT_DISPENSE_BEHAVIOR
            : fallback;
    }

    @Override
    protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
        delegatedToFallback = false;

        if (!DinnerwareConfig.dispensersPlacePlates()) {
            return dispenseFallback(source, stack);
        }

        ItemStack result = super.execute(source, stack);

        if (!isSuccess()) {
            return dispenseFallback(source, stack);
        }

        return result;
    }

    private ItemStack dispenseFallback(BlockSource source, ItemStack stack) {
        delegatedToFallback = true;
        setSuccess(false);

        ItemStack result = fallback.dispense(source, stack);

        if (fallback instanceof OptionalDispenseItemBehavior optional && !optional.isSuccess()) {
            return DEFAULT_DISPENSE_BEHAVIOR.dispense(source, result);
        }

        return result;
    }

    @Override
    protected void playSound(@NotNull BlockSource source) {
        if (!delegatedToFallback) super.playSound(source);
    }

    @Override
    protected void playAnimation(@NotNull BlockSource source, @NotNull Direction direction) {
        if (!delegatedToFallback) super.playAnimation(source, direction);
    }
}