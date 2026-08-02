package net.v972.dinnerware.fabric.datagen;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.registry.PlateDefinition;
import net.v972.dinnerware.util.DinnerwareHelper;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class DinnerwareFabricRecipeProvider extends FabricRecipeProvider {

    public DinnerwareFabricRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        generatePlateRecipes(exporter);
        generateTerracottaDyeingRecipes(exporter);
        generateTrayRecipes(exporter);
    }

    private static void generatePlateRecipes(Consumer<FinishedRecipe> writer) {
        for (Block block : FabricModBlocks.getKnownPlateBlocksIterable()) {
            PlateBlock plateBlock = (PlateBlock) block;

            Item[] craftingItems = Arrays.stream(plateBlock.CRAFTING_MATERIAL.getItems())
                .map(ItemStack::getItem)
                .toArray(Item[]::new);
            // fallback because tags are being pissy
            if (craftingItems.length == 0 || Arrays.stream(craftingItems).allMatch(i -> i == Items.BARRIER)
            ) craftingItems = new Item[] { plateBlock.asItem() };

            var recipe = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                    plateBlock, plateBlock.CRAFTING_AMOUNT);

            // special case for iron to NOT override vanilla bucket
            if (plateBlock == FabricModBlocks.plateBlock(PlateDefinition.IRON)) {
                recipe.pattern("MMM");
            } else {
                recipe.pattern("M M")
                      .pattern(" M ");
            }

            recipe
                .define('M', plateBlock.CRAFTING_MATERIAL)
                .unlockedBy(
                    "has_" + DinnerwareHelper.getBlockId(plateBlock) + "_ingredients_or_self",
                    InventoryChangeTrigger.TriggerInstance.hasItems(craftingItems)
                )
                .save(writer);
        }
    }

    private static void generateTerracottaDyeingRecipes(Consumer<FinishedRecipe> writer) {
        Block baseTerracottaPlate = FabricModBlocks.plateBlock(PlateDefinition.TERRACOTTA);

        for (Pair<Block, Ingredient> pair : FabricModBlocks.getTerracottaPlateBlocksDyeingMap()) {
            PlateBlock plateBlock = (PlateBlock) pair.getFirst();
            Ingredient dyeItems = pair.getSecond();

            Item[] unlockItems =
                Stream.concat(
                    Arrays.stream(plateBlock.CRAFTING_MATERIAL.getItems()).map(ItemStack::getItem),
                    Arrays.stream(dyeItems.getItems()).map(ItemStack::getItem)
                ).toArray(Item[]::new);

            String blockId = DinnerwareHelper.getBlockId(plateBlock);

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, plateBlock, 8)
                .pattern("BBB")
                .pattern("BDB")
                .pattern("BBB")
                .define('D', dyeItems)
                .define('B', baseTerracottaPlate)
                .unlockedBy(
                    "has_" + blockId + "_ingredients_dyeing",
                    InventoryChangeTrigger.TriggerInstance.hasItems(unlockItems)
                )
                .save(writer, DinnerwareConstants.id(blockId + "_from_dyeing"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plateBlock, 1)
                .requires(baseTerracottaPlate)
                .requires(dyeItems)
                .unlockedBy(
                    "has_" + blockId + "_ingredients_dyeing",
                    InventoryChangeTrigger.TriggerInstance.hasItems(unlockItems)
                )
                .save(writer, DinnerwareConstants.id(blockId + "_from_dyeing_single"));
        }
    }

    private static void generateTrayRecipes(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FabricModItems.ironTray())
            .pattern("IPI")
            .define('P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
            .define('I', ConventionalItemTags.IRON_INGOTS)
            .unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
            .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FabricModItems.goldTray())
            .pattern("IPI")
            .define('P', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
            .define('I', ConventionalItemTags.GOLD_INGOTS)
            .unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
            .save(writer);
    }
}