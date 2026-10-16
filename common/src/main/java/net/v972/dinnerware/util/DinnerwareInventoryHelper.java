package net.v972.dinnerware.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.item.DinnerwareItems;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class DinnerwareInventoryHelper {
    private DinnerwareInventoryHelper() {
    }

    public static OptionalInt getFirstNonEmptySlot(NonNullList<ItemStack> pList) {
        return IntStream.range(0, pList.size())
            .filter(i -> !pList.get(i).isEmpty())
            .findFirst();
    }

    public static int getNonEmptySlotsCount(NonNullList<ItemStack> pList) {
        return (int) IntStream
            .range(0, pList.size())
            .filter(i -> !pList.get(i).isEmpty())
            .count();
    }

    public static NonNullList<ItemStack> plateContentFromItem(ItemStack pStack, HolderLookup.Provider registries) {
        NonNullList<ItemStack> result = NonNullList.withSize(3, ItemStack.EMPTY);

        CustomData blockEntityData = pStack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (blockEntityData == null) {
            return result;
        }

        CompoundTag tag = blockEntityData.copyTag();
        if (!tag.contains(PlateBlockBlockEntity.ITEMS_TAG, Tag.TAG_COMPOUND)) {
            return result;
        }

        CompoundTag inventoryTag = tag.getCompound(PlateBlockBlockEntity.ITEMS_TAG);
        if (inventoryTag.contains("Size", Tag.TAG_INT)) {
            result = NonNullList.withSize(inventoryTag.getInt("Size"), ItemStack.EMPTY);
        }

        ContainerHelper.loadAllItems(inventoryTag, result, registries);

        return result;
    }

    public static NonNullList<ItemStack> trayContentFromItem(ItemStack pStack) {
        ItemContainerContents contents = pStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

        List<ItemStack> stacks = contents
            .nonEmptyStream()
            .map(ItemStack::copy)
            .toList();

        if (stacks.isEmpty()) {
            return NonNullList.withSize(64, ItemStack.EMPTY);
        }

        NonNullList<ItemStack> result = NonNullList.withSize(stacks.size(), ItemStack.EMPTY);

        for (int i = 0; i < stacks.size(); i++) {
            result.set(stacks.size() - (i + 1), stacks.get(i));
        }

        return result;
    }
}