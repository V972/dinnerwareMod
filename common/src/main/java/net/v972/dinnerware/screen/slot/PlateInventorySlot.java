package net.v972.dinnerware.screen.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlateInventorySlot extends Slot {
    public PlateInventorySlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return this.container.canPlaceItem(this.getContainerSlot(), stack);
    }

    @Override
    public int getMaxStackSize() {
        return this.container.getMaxStackSize();
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return Math.min(this.getMaxStackSize(), stack.getMaxStackSize());
    }
}