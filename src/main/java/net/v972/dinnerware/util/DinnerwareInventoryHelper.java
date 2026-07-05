package net.v972.dinnerware.util;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.item.DinnerwareItems;

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

    public static boolean hasPlateInside(ItemStack pStack) {
        if (!pStack.is(ModTags.Items.PLATES)) return false;

        CompoundTag nbt = pStack.getTag();
        if (nbt == null || nbt.isEmpty()) return false;

        nbt = nbt.getCompound("BlockEntityTag").getCompound("Inventory");

        if (nbt.isEmpty() || nbt.contains("Items")) return false;

        ListTag listTag = nbt.getList("Items", 10);
        Set<Item> plateItemsSet =
            listTag.stream()
                .map(tag -> ItemStack.of((CompoundTag) tag).getItem())
                .collect(Collectors.toSet());
        Set<Item> allPlatesSet = DinnerwareItems.getKnownPlateItemsSet();
        plateItemsSet.retainAll(allPlatesSet);
        return !plateItemsSet.isEmpty();
    }

    public static NonNullList<ItemStack> plateContentFromNBT(CompoundTag pTag) {
        NonNullList<ItemStack> result = NonNullList.withSize(3, ItemStack.EMPTY);

        if (pTag != null) {
            CompoundTag nbt = pTag.getCompound("BlockEntityTag").getCompound("Inventory");

            if (nbt.isEmpty()) return result;

            if (nbt.contains("Size", Tag.TAG_INT))
                result = NonNullList.withSize(nbt.getInt("Size"), ItemStack.EMPTY);

            ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag itemTags = tagList.getCompound(i);
                int slot = itemTags.getInt("Slot");

                if (slot >= 0 && slot < result.size()) {
                    result.set(slot, ItemStack.of(itemTags));
                }
            }
        }

        return result;
    }

    public static NonNullList<ItemStack> trayContentFromNBT(CompoundTag pTag) {
        if (pTag != null && pTag.contains("Items")) {
            ListTag tagList = pTag.getList("Items", 10);

            int listSize = tagList.size();
            if (listSize == 0) return NonNullList.withSize(64, ItemStack.EMPTY);

            NonNullList<ItemStack> result = NonNullList.withSize(listSize, ItemStack.EMPTY);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag itemTags = tagList.getCompound(i);
                result.set(listSize - (i + 1), ItemStack.of(itemTags));
            }

            return result;
        }

        return NonNullList.withSize(64, ItemStack.EMPTY);
    }
}