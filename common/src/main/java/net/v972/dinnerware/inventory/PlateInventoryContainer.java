package net.v972.dinnerware.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class PlateInventoryContainer implements Container {
    private final PlateInventory inventory;
    private final ContainerValidity validity;

    public PlateInventoryContainer(PlateInventory inventory, ContainerValidity validity) {
        this.inventory = inventory;
        this.validity = validity;
    }

    @Override
    public int getContainerSize() {
        return inventory.getSlotCount();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return inventory.extractItem(slot, amount, false);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return inventory.extractItem(slot, inventory.getStackInSlot(slot).getCount(), false);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
    }

    @Override
    public void setChanged() {
        // PlateInventory already calls its onChanged callback.
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return validity.stillValid(player);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return inventory.canPlaceItem(slot, stack);
    }

    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return inventory.insertItem(slot, stack, simulate);
    }

    public int getSlotLimit(int slot) {
        return this.inventory.getSlotLimit(slot);
    }

    @Override
    public int getMaxStackSize() {
        int limit = 64;

        for (int slot = 0; slot < inventory.getSlotCount(); slot++) {
            limit = Math.min(limit, inventory.getSlotLimit(slot));
        }

        return limit;
    }

    @FunctionalInterface
    public interface ContainerValidity {
        boolean stillValid(Player player);
    }
}