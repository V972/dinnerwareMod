package net.v972.dinnerware.fabric.inventory;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.inventory.PlateInventory;

import java.util.ArrayList;
import java.util.List;

public final class FabricPlateInventoryStorage {
    private FabricPlateInventoryStorage() {
    }

    public static Storage<ItemVariant> of(PlateInventory inventory) {
        List<SingleStackStorage> slots = new ArrayList<>(inventory.getSlotCount());

        for (int slot = 0; slot < inventory.getSlotCount(); slot++) {
            slots.add(new PlateSlotStorage(inventory, slot));
        }

        return new CombinedStorage<>(slots);
    }

    private static final class PlateSlotStorage extends SingleStackStorage {
        private final PlateInventory inventory;
        private final int slot;

        private PlateSlotStorage(PlateInventory inventory, int slot) {
            this.inventory = inventory;
            this.slot = slot;
        }

        @Override
        protected ItemStack getStack() {
            return inventory.getStackInSlot(slot);
        }

        @Override
        protected void setStack(ItemStack stack) {
            // not overriding onFinalCommit() cause this here already call it inside
            inventory.setStackInSlot(slot, stack);
        }

        @Override
        protected boolean canInsert(ItemVariant variant) {
            return inventory.canPlaceItem(
                slot,
                variant.toStack(1)
            );
        }

        @Override
        protected int getCapacity(ItemVariant variant) {
            if (variant.isBlank()) {
                return inventory.getSlotLimit(slot);
            }

            return Math.min(
                inventory.getSlotLimit(slot),
                variant.getObject().getMaxStackSize()
            );
        }
    }
}