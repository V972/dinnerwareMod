package net.v972.dinnerware.fabric.inventory;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.inventory.PlateInventory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class FabricPlateInventoryStorage {
    private static final Map<PlateInventory, WeakReference<Storage<ItemVariant>>> CACHE = new WeakHashMap<>();

    private FabricPlateInventoryStorage() { }

    public static synchronized Storage<ItemVariant> of(PlateInventory inventory) {
        WeakReference<Storage<ItemVariant>> reference = CACHE.get(inventory);
        Storage<ItemVariant> storage = reference != null ? reference.get() : null;

        if (storage == null) {
            List<SingleStackStorage> slots = new ArrayList<>(inventory.getSlotCount());

            for (int slot = 0; slot < inventory.getSlotCount(); slot++)
                slots.add(new PlateSlotStorage(inventory, slot));

            storage = new CombinedStorage<>(slots);
            CACHE.put(inventory, new WeakReference<>(storage));
        }

        return storage;
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
            // not overriding onFinalCommit() cause this here already calls it inside
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