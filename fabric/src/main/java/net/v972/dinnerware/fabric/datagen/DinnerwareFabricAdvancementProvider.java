package net.v972.dinnerware.fabric.datagen;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.advancement.ManualCriterionTrigger;
import net.v972.dinnerware.datagen.DinnerwareDatagenPaths;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.function.Consumer;

public final class DinnerwareFabricAdvancementProvider
        extends FabricAdvancementProvider {

    public DinnerwareFabricAdvancementProvider(FabricDataOutput output) {
        super(
            new FabricDataOutput(
                output.getModContainer(),
                DinnerwareDatagenPaths.commonOutput(),
                output.isStrictValidationEnabled()
            )
        );
    }

    @Override
    public void generateAdvancement(
            Consumer<Advancement> consumer
    ) {
        Advancement root = Advancement.Builder.advancement()
            .display(
                FabricModItems.icon(),
                Component.translatable("advancement.dinnerware.root.title"),
                Component.translatable("advancement.dinnerware.root.description"),
                new ResourceLocation("minecraft", "textures/block/quartz_block_top.png"),
                FrameType.TASK, false, false, true
            )
            .addCriterion("for_free", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] {}))
            .save(consumer, DinnerwareConstants.id("root").toString());

        Advancement finestChina = Advancement.Builder.advancement()
            .parent(root)
            .display(
                FabricModBlocks.plateBlock(PlateDefinition.DIAMOND),
                Component.translatable("advancement.dinnerware.get_diamond_plate.title"),
                Component.translatable("advancement.dinnerware.get_diamond_plate.description"),
                null, FrameType.TASK, true, false, false
            )
            .addCriterion("got_diamond_plate",
                InventoryChangeTrigger.TriggerInstance.hasItems(
                    FabricModItems.plateItem(PlateDefinition.DIAMOND)
                )
            )
            .save(consumer, DinnerwareConstants.id("get_diamond_plate").toString());

        ItemStack inceptionPlateStack = new ItemStack(
            FabricModItems.plateItem(PlateDefinition.IRON)
        );
        CompoundTag inceptionPlateTag;
        try {
            inceptionPlateTag = TagParser.parseTag(
                "{BlockEntityTag: {Inventory: {Size: 3, Items: " +
                        "[{Slot: 2, id: \"dinnerware:gold_plate\", " +
                        "Count: 1b, tag: {BlockEntityTag: {Inventory: " +
                        "{Size: 3, Items: [{Slot: 2, " +
                        "id: \"dinnerware:diamond_plate\", Count: 1b}]}, " +
                        "id: \"dinnerware:plate_block\"}}}]}, " +
                        "id: \"dinnerware:plate_block\"}}"
            );
        } catch (CommandSyntaxException exception) {
            throw new IllegalStateException(
                "Failed to create inception plate advancement icon",
                exception
            );
        }
        inceptionPlateStack.setTag(inceptionPlateTag);

        Advancement inception = Advancement.Builder.advancement()
            .parent(root)
            .display(
                inceptionPlateStack,
                Component.translatable("advancement.dinnerware.put_plate_in_plate.title"),
                Component.translatable("advancement.dinnerware.put_plate_in_plate.description"),
                null, FrameType.GOAL, true, false, false
            )
            .addCriterion("put_plate_in_plate",
                ManualCriterionTrigger.TriggerInstance.byId(
                    DinnerwareConstants.id("put_plate_in_plate")
                )
            )
            .save(consumer, DinnerwareConstants.id("put_plate_in_plate").toString());

        Advancement oneTrayToRuleThemAll = Advancement.Builder.advancement()
            .parent(root)
            .display(
                FabricModItems.ironTray(),
                Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.title"),
                Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.description"),
                null, FrameType.CHALLENGE, true, true, false
            )
            .addCriterion("one_tray_to_hold_them_all",
                ManualCriterionTrigger.TriggerInstance.byId(
                    DinnerwareConstants.id("one_tray_to_hold_them_all")
                )
            )
            .save(consumer, DinnerwareConstants.id("get_tray_with_all_regular_plates").toString());
    }
}