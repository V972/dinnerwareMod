package net.v972.dinnerware.datagen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, plateBlock, plateBlock.CRAFTING_AMOUNT)
                .pattern("M M")
                .pattern(" M ")
                .define('M', plateBlock.CRAFTING_MATERIAL)
                .unlockedBy(
                    "has_" + DinnerwareHelper.getBlockId(plateBlock) + "_ingredients",
                    InventoryChangeTrigger.TriggerInstance.hasItems(
                        Arrays.stream(plateBlock.CRAFTING_MATERIAL.getItems())
                            .map(itemStack -> itemStack.getItem())
                            .toArray(Item[]::new)
                    )
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

            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, plateBlock, 8)
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

//        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TRAY.get())
//                .pattern("IPI")
//                .define('P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
//                .define('I', Tags.Items.INGOTS_IRON)
//                .unlockedBy(getHasName(ModItems.TRAY.get()), has(ModItems.TRAY.get()))
//                .save(pWriter);

    }
}
