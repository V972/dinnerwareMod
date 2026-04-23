package net.v972.dinnerware.datagen;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.advancement.ManualCriterionTrigger;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends ForgeAdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Generator()));
    }

    public static class Generator implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            Advancement root = Advancement.Builder.advancement()
                .display(
                    ModItems.ICON.get(),
                    Component.translatable("advancement.dinnerware.root.title"),
                    Component.translatable("advancement.dinnerware.root.description"),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/quartz_block_top.png"),
                    FrameType.TASK, false, false, true
                )
                .addCriterion("for_free",
                    InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] {})
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "root"), existingFileHelper);

            Advancement finestChina = Advancement.Builder.advancement()
                .parent(root)
                .display(
                    ModBlocks.PLATE_BLOCK_DIAMOND.get(),
                    Component.translatable("advancement.dinnerware.get_diamond_plate.title"),
                    Component.translatable("advancement.dinnerware.get_diamond_plate.description"),
                    null, FrameType.TASK, true, false, false
                )
                .addCriterion("got_diamond_plate",
                    InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.PLATE_ITEM_DIAMOND.get()
                    )
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "get_diamond_plate"), existingFileHelper);

            ItemStack inceptionPlateStack = new ItemStack(ModItems.PLATE_ITEM_IRON.get());
            CompoundTag tag = null;
            try {
                tag = TagParser.parseTag("{BlockEntityTag: {Inventory: {Size: 3, Items: [{Slot: 2, id: \"dinnerware:gold_plate\", Count: 1b, tag: {BlockEntityTag: {Inventory: {Size: 3, Items: [{Slot: 2, id: \"dinnerware:diamond_plate\", Count: 1b}]}, id: \"dinnerware:plate_block\"}}}]}, id: \"dinnerware:plate_block\"}}");
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
            inceptionPlateStack.setTag(tag);
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
                        ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "put_plate_in_plate")
                    )
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "put_plate_in_plate"), existingFileHelper);

            Advancement oneTrayToRuleThemAll = Advancement.Builder.advancement()
                .parent(root)
                .display(
                    ModItems.TRAY_IRON.get(),
                    Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.title"),
                    Component.translatable("advancement.dinnerware.get_tray_with_all_regular_plates.description"),
                    null, FrameType.CHALLENGE, true, true, false
                )
                .addCriterion("one_tray_to_hold_them_all",
                    ManualCriterionTrigger.TriggerInstance.byId(
                        ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "one_tray_to_hold_them_all")
                    )
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, "get_tray_with_all_regular_plates"), existingFileHelper);
        }
    }
}
