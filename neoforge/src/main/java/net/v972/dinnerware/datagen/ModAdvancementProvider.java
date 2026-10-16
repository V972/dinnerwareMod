package net.v972.dinnerware.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.advancement.ManualCriterionTrigger;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.forge.registry.ForgeModBlockEntities;
import net.v972.dinnerware.forge.registry.ForgeModBlocks;
import net.v972.dinnerware.forge.registry.ForgeModCriterionTriggers;
import net.v972.dinnerware.forge.registry.ForgeModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Generator()));
    }

    public static class Generator implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                    ForgeModItems.ICON.get(),
                    Component.translatable("advancement.dinnerware.root.title"),
                    Component.translatable("advancement.dinnerware.root.description"),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/quartz_block_top.png"),
                    AdvancementType.TASK, false, false, true
                )
                .addCriterion("for_free",
                    InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] {})
                )
                .save(saver, DinnerwareConstants.id("root"), existingFileHelper);

            AdvancementHolder finestChina = Advancement.Builder.advancement()
                .parent(root)
                .display(
                    ForgeModBlocks.PLATE_BLOCK_DIAMOND.get(),
                    Component.translatable("advancement.dinnerware.get_diamond_plate.title"),
                    Component.translatable("advancement.dinnerware.get_diamond_plate.description"),
                    null, AdvancementType.TASK, true, false, false
                )
                .addCriterion("got_diamond_plate",
                    InventoryChangeTrigger.TriggerInstance.hasItems(
                        ForgeModItems.PLATE_ITEM_DIAMOND.get()
                    )
                )
                .save(saver, DinnerwareConstants.id("get_diamond_plate"), existingFileHelper);

            ItemStack inceptionPlateStack = createInceptionPlateStack(registries);

            AdvancementHolder inception = Advancement.Builder.advancement()
                .parent(root)
                .display(
                    inceptionPlateStack,
                    Component.translatable("advancement.dinnerware.put_plate_in_plate.title"),
                    Component.translatable("advancement.dinnerware.put_plate_in_plate.description"),
                    null, AdvancementType.GOAL, true, false, false
                )
                .addCriterion("put_plate_in_plate",
                    ManualCriterionTrigger.TriggerInstance.byId(
                        ForgeModCriterionTriggers.manualTrigger(),
                        DinnerwareConstants.id("put_plate_in_plate"))
                )
                .save(saver, DinnerwareConstants.id("put_plate_in_plate"), existingFileHelper);

            AdvancementHolder oneTrayToRuleThemAll = Advancement.Builder.advancement()
                .parent(root)
                .display(
                    ForgeModItems.TRAY_IRON.get(),
                    Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.title"),
                    Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.description"),
                    null, AdvancementType.CHALLENGE, true, true, false
                )
                .addCriterion("one_tray_to_hold_them_all",
                    ManualCriterionTrigger.TriggerInstance.byId(
                        ForgeModCriterionTriggers.manualTrigger(),
                        DinnerwareConstants.id("one_tray_to_hold_them_all"))
                )
                .save(saver, DinnerwareConstants.id("get_tray_with_all_regular_plates"), existingFileHelper);
        }
    }

    private static ItemStack createInceptionPlateStack(HolderLookup.Provider registries) {
        ItemStack diamondPlate = new ItemStack(ForgeModItems.PLATE_ITEM_DIAMOND.get());
        ItemStack goldPlate = new ItemStack(ForgeModItems.PLATE_ITEM_GOLD.get());
        ItemStack inceptionPlateStack = new ItemStack(ForgeModItems.PLATE_ITEM_IRON.get());


        NonNullList<ItemStack> goldContents = NonNullList.withSize(PlateBlockBlockEntity.SLOT_COUNT, ItemStack.EMPTY);
        goldContents.set(2, diamondPlate);

        CompoundTag goldInventoryTag = new CompoundTag();
        ContainerHelper.saveAllItems(goldInventoryTag, goldContents, registries);

        CompoundTag goldBlockEntityTag = new CompoundTag();
        goldBlockEntityTag.put(PlateBlockBlockEntity.ITEMS_TAG, goldInventoryTag);

        BlockItem.setBlockEntityData(goldPlate, ForgeModBlockEntities.plateBlockEntityType(), goldBlockEntityTag);


        NonNullList<ItemStack> ironContents = NonNullList.withSize(PlateBlockBlockEntity.SLOT_COUNT, ItemStack.EMPTY);
        ironContents.set(2, goldPlate);

        CompoundTag ironInventoryTag = new CompoundTag();
        ContainerHelper.saveAllItems(ironInventoryTag, ironContents, registries);

        CompoundTag ironBlockEntityTag = new CompoundTag();
        ironBlockEntityTag.put(PlateBlockBlockEntity.ITEMS_TAG, ironInventoryTag);

        BlockItem.setBlockEntityData(inceptionPlateStack, ForgeModBlockEntities.plateBlockEntityType(), ironBlockEntityTag);


        return inceptionPlateStack;
    }
}
