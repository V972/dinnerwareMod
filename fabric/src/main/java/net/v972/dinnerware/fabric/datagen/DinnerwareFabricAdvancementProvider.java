package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.advancement.ManualCriterionTrigger;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.datagen.DinnerwareDatagenPaths;
import net.v972.dinnerware.fabric.registry.FabricModBlockEntities;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class DinnerwareFabricAdvancementProvider extends FabricAdvancementProvider {

    public DinnerwareFabricAdvancementProvider(FabricDataOutput output,
                       CompletableFuture<HolderLookup.Provider> registries) {
        super(
            new FabricDataOutput(
                output.getModContainer(),
                DinnerwareDatagenPaths.commonOutput(),
                output.isStrictValidationEnabled()
            ),
            registries
        );
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder root = Advancement.Builder.advancement()
            .display(
                FabricModItems.icon(),
                Component.translatable("advancement.dinnerware.root.title"),
                Component.translatable("advancement.dinnerware.root.description"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/quartz_block_top.png"),
                AdvancementType.TASK, false, false, true
            )
            .addCriterion("for_free", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] {}))
            .save(consumer, DinnerwareConstants.id("root").toString());

        AdvancementHolder finestChina = Advancement.Builder.advancement()
            .parent(root)
            .display(
                FabricModBlocks.plateBlock(PlateDefinition.DIAMOND),
                Component.translatable("advancement.dinnerware.get_diamond_plate.title"),
                Component.translatable("advancement.dinnerware.get_diamond_plate.description"),
                null, AdvancementType.TASK, true, false, false
            )
            .addCriterion("got_diamond_plate",
                InventoryChangeTrigger.TriggerInstance.hasItems(
                    FabricModItems.plateItem(PlateDefinition.DIAMOND)
                )
            )
            .save(consumer, DinnerwareConstants.id("get_diamond_plate").toString());

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
                    DinnerwareConstants.id("put_plate_in_plate")
                )
            )
            .save(consumer, DinnerwareConstants.id("put_plate_in_plate").toString());

        AdvancementHolder oneTrayToRuleThemAll = Advancement.Builder.advancement()
            .parent(root)
            .display(
                FabricModItems.ironTray(),
                Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.title"),
                Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.description"),
                null, AdvancementType.CHALLENGE, true, true, false
            )
            .addCriterion("one_tray_to_hold_them_all",
                ManualCriterionTrigger.TriggerInstance.byId(
                    DinnerwareConstants.id("one_tray_to_hold_them_all")
                )
            )
            .save(consumer, DinnerwareConstants.id("get_tray_with_all_regular_plates").toString());
    }

    private static ItemStack createInceptionPlateStack(HolderLookup.Provider registries) {
        ItemStack diamondPlate = new ItemStack(FabricModItems.plateItem(PlateDefinition.DIAMOND));
        ItemStack goldPlate = new ItemStack(FabricModItems.plateItem(PlateDefinition.GOLD));
        ItemStack inceptionPlateStack = new ItemStack(FabricModItems.plateItem(PlateDefinition.IRON));


        NonNullList<ItemStack> goldContents = NonNullList.withSize(PlateBlockBlockEntity.SLOT_COUNT, ItemStack.EMPTY);
        goldContents.set(2, diamondPlate);

        CompoundTag goldInventoryTag = new CompoundTag();
        ContainerHelper.saveAllItems(goldInventoryTag, goldContents, registries);

        CompoundTag goldBlockEntityTag = new CompoundTag();
        goldBlockEntityTag.put(PlateBlockBlockEntity.ITEMS_TAG, goldInventoryTag);

        BlockItem.setBlockEntityData(goldPlate, FabricModBlockEntities.plateBlockEntityType(), goldBlockEntityTag);


        NonNullList<ItemStack> ironContents = NonNullList.withSize(PlateBlockBlockEntity.SLOT_COUNT, ItemStack.EMPTY);
        ironContents.set(2, goldPlate);

        CompoundTag ironInventoryTag = new CompoundTag();
        ContainerHelper.saveAllItems(ironInventoryTag, ironContents, registries);

        CompoundTag ironBlockEntityTag = new CompoundTag();
        ironBlockEntityTag.put(PlateBlockBlockEntity.ITEMS_TAG, ironInventoryTag);

        BlockItem.setBlockEntityData(inceptionPlateStack, FabricModBlockEntities.plateBlockEntityType(), ironBlockEntityTag);


        return inceptionPlateStack;
    }
}