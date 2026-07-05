package net.v972.dinnerware.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.v972.dinnerware.config.DinnerwareConfig;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public final class PlateInventory {
    private final NonNullList<ItemStack> items;
    private final Runnable onChanged;

    public PlateInventory(int slotCount, Runnable onChanged) {
        this.items = NonNullList.withSize(slotCount, ItemStack.EMPTY);
        this.onChanged = onChanged;
    }

    public int getSlotCount() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for (ItemStack stack : this.items) {
            if (!stack.isEmpty()) return false;
        }

        return true;
    }

    public int getNonEmptySlotsCount() {
        return (int) IntStream
            .range(0, getSlotCount())
            .filter(i -> !getStackInSlot(i).isEmpty())
            .count();
    }

    public OptionalInt getFirstNonEmptySlot(boolean validateBlacklist) {
        return IntStream.range(0, getSlotCount())
            .filter(i ->
                !getStackInSlot(i).isEmpty() &&
                (!validateBlacklist || !DinnerwareConfig.isInFoodBlacklist(getStackInSlot(i)))
            )
            .findFirst();
    }

    public boolean hasPlateInside() {
        for (ItemStack stack : this.items) {
            if (stack.is(ModTags.Items.PLATES)) return true;
        }

        return false;
    }

    public @NotNull ItemStack getStackInSlot(int slot) {
        validateSlot(slot);
        return this.items.get(slot);
    }

    public @NotNull ItemStack getStackCopyInSlot(int slot) {
        return getStackInSlot(slot).copy();
    }

    public NonNullList<ItemStack> getStackCopies() {
        NonNullList<ItemStack> result = NonNullList.withSize(getSlotCount(), ItemStack.EMPTY);

        for (int i = 0; i < getSlotCount(); i++) {
            result.set(i, getStackCopyInSlot(i));
        }

        return result;
    }

    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        validateSlot(slot);

        ItemStack stackToStore = stack;
        if (!stackToStore.isEmpty() && stackToStore.getCount() > getSlotLimit(slot)) {
            stackToStore = stackToStore.copy();
            stackToStore.setCount(getSlotLimit(slot));
        }

        this.items.set(slot, stackToStore);
        changed();
    }

    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        validateSlot(slot);

        if (amount <= 0) return ItemStack.EMPTY;

        ItemStack existing = getStackInSlot(slot);
        if (existing.isEmpty()) return ItemStack.EMPTY;

        int extractedCount = Math.min(amount, existing.getCount());
        ItemStack extracted = existing.copy();
        extracted.setCount(extractedCount);

        if (!simulate) {
            existing.shrink(extractedCount);
            if (existing.isEmpty()) {
                this.items.set(slot, ItemStack.EMPTY);
            }

            changed();
        }

        return extracted;
    }

    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        validateSlot(slot);

        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (!canPlaceItem(slot, stack)) return stack;

        ItemStack existing = getStackInSlot(slot);
        int slotLimit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());

        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameTags(existing, stack)) {
                return stack;
            }

            slotLimit -= existing.getCount();
        }

        if (slotLimit <= 0) return stack;

        int insertedCount = Math.min(stack.getCount(), slotLimit);

        if (!simulate) {
            if (existing.isEmpty()) {
                ItemStack inserted = stack.copy();
                inserted.setCount(insertedCount);
                this.items.set(slot, inserted);
            } else {
                existing.grow(insertedCount);
            }

            changed();
        }

        if (stack.getCount() == insertedCount) {
            return ItemStack.EMPTY;
        }

        ItemStack remainder = stack.copy();
        remainder.shrink(insertedCount);
        return remainder;
    }

    public void clearContent() {
        clearContentWithoutUpdate();
        changed();
    }

    public void clearContentWithoutUpdate() {
        for (int i = 0; i < this.items.size(); i++) {
            this.items.set(i, ItemStack.EMPTY);
        }
    }

    public void save(CompoundTag tag) {
        tag.putInt("Size", getSlotCount());
        ContainerHelper.saveAllItems(tag, this.items);
    }

    public void load(CompoundTag tag) {
        clearContentWithoutUpdate();
        ContainerHelper.loadAllItems(tag, this.items);
    }

    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        validateSlot(slot);
        return canPlaceItemOnPlate(stack);
    }

    public int getSlotLimit(int slot) {
        validateSlot(slot);
        return DinnerwareConfig.maxPlateStackSize();
    }

    private boolean canPlaceItemOnPlate(ItemStack stack) {
        return
            !DinnerwareConfig.onlyFoodOnPlate() ||
                (stack.isEdible() &&
                !(stack.getItem() instanceof BowlFoodItem) &&
                !(stack.getItem() instanceof SuspiciousStewItem)
                ) ||
                stack.is(ModTags.Items.ADDITIONAL_FOOD);
    }

    private void validateSlot(int slot) {
        if (slot < 0 || slot >= getSlotCount()) {
            throw new IndexOutOfBoundsException("Invalid plate inventory slot: " + slot);
        }
    }

    private void changed() {
        this.onChanged.run();
    }
}