package net.v972.dinnerware.datagen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.v972.dinnerware.util.DinnerwareHelper;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        for (Block block : ModBlocks.getKnownBlocks()) {
            PlateBlock plateBlock = (PlateBlock)block;

            var craftingItems =
                    Arrays.stream(plateBlock.CRAFTING_MATERIAL.getItems())
                    .map(ItemStack::getItem)
                    .toArray(Item[]::new);

            var recipe = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                plateBlock, plateBlock.CRAFTING_AMOUNT);

            // special case for iron to NOT override vanilla bucket
            if (Arrays.stream(craftingItems).anyMatch(item -> item.getDefaultInstance().is(Items.IRON_INGOT))) {
                recipe
                    .pattern("MMM");
            } else {
                recipe
                    .pattern("M M")
                    .pattern(" M ");
            }

            recipe
                .define('M', plateBlock.CRAFTING_MATERIAL)
                .unlockedBy(
                    "has_" + DinnerwareHelper.getBlockId(plateBlock) + "_ingredients",
                    InventoryChangeTrigger.TriggerInstance.hasItems(craftingItems)
                )
                .save(pWriter);
        }

        for (Pair<Block, Ingredient> blockIngredientPair : ModBlocks.getTerracottaDyeingMap()) {
            PlateBlock plateBlock = (PlateBlock)blockIngredientPair.getFirst();
            Block baseTerracottaPlate = ModBlocks.PLATE_BLOCK_TERRACOTTA.get();
            Ingredient dyeItems = blockIngredientPair.getSecond();

            Item[] unlockItems =
                    Stream.concat(
                        Arrays.stream(plateBlock.CRAFTING_MATERIAL.getItems()).map(ItemStack::getItem),
                        Arrays.stream(dyeItems.getItems()).map(ItemStack::getItem)
                    ).toArray(Item[]::new);

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, plateBlock, 8)
                .pattern("BBB")
                .pattern("BDB")
                .pattern("BBB")
                .define('D', dyeItems)
                .define('B', baseTerracottaPlate)
                .unlockedBy(
                        "has_" + DinnerwareHelper.getBlockId(plateBlock) + "_ingredients_dyeing",
                        InventoryChangeTrigger.TriggerInstance.hasItems(unlockItems)
                )
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(
                        DinnerwareMod.MOD_ID, DinnerwareHelper.getBlockId(plateBlock) + "_from_dyeing")
                );

            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, plateBlock, 1)
                .requires(baseTerracottaPlate)
                .requires(dyeItems)
                .unlockedBy(
                        "has_" + DinnerwareHelper.getBlockId(plateBlock) + "_ingredients_dyeing",
                        InventoryChangeTrigger.TriggerInstance.hasItems(unlockItems)
                )
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(
                        DinnerwareMod.MOD_ID, DinnerwareHelper.getBlockId(plateBlock) + "_from_dyeing_single")
                );
        }

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TRAY_IRON.get())
                .pattern("IPI")
                .define('P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TRAY_GOLD.get())
                .pattern("IPI")
                .define('P', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .define('I', Tags.Items.INGOTS_GOLD)
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .save(pWriter);
    }
}
