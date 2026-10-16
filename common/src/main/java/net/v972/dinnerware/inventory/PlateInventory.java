package net.v972.dinnerware.inventory;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
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
        this.items.set(slot, stack);
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
            if (!ItemStack.isSameItemSameComponents(existing, stack)) {
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

    public void save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("Size", getSlotCount());
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }

    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        clearContentWithoutUpdate();
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        validateSlot(slot);
        return canPlaceItemOnPlate(slot, stack);
    }

    public int getSlotLimit(int slot) {
        validateSlot(slot);
        return DinnerwareConfig.maxPlateStackSize();
    }

    private boolean canPlaceItemOnPlate(int slot, ItemStack stack) {
        if (!DinnerwareConfig.onlyFoodOnPlate() || stack.is(ModTags.Items.ADDITIONAL_FOOD)) {
            return true;
        }

        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food == null) {
            return false;
        }

        boolean isFoodContainerFood = food.usingConvertsTo()
            .map(remainder -> remainder.is(ModTags.Items.FOOD_CONTAINERS))
            .orElse(false);

        boolean isDrinkContainerFood = food.usingConvertsTo()
            .map(remainder -> remainder.is(ModTags.Items.DRINK_CONTAINERS))
            .orElse(false);

        return !isFoodContainerFood && (!isFoodSlot(slot) || !isDrinkContainerFood);
    }

    private boolean isFoodSlot(int slot) {
        return true;
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