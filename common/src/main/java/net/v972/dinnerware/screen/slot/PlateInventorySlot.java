package net.v972.dinnerware.screen.slot;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.inventory.PlateInventoryContainer;
import org.jetbrains.annotations.NotNull;

public class PlateInventorySlot extends Slot {
    private final PlateInventoryContainer plateContainer;

    public PlateInventorySlot(PlateInventoryContainer container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.plateContainer = container;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return this.container.canPlaceItem(this.getContainerSlot(), stack);
    }

    @Override
    public int getMaxStackSize() {
        return this.plateContainer.getSlotLimit(this.getContainerSlot());
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return Math.min(getMaxStackSize(), stack.getMaxStackSize());
    }
}