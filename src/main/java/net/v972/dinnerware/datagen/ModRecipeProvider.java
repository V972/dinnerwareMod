package net.v972.dinnerware.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Arrays;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        for (Block block : ModBlocks.getKnownBlocks()) {
            PlateBlock plateBlock = (PlateBlock)block;

            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, plateBlock, plateBlock.CRAFTING_AMOUNT)
                .pattern("M M")
                .pattern(" M ")
                .define('M', plateBlock.CRAFTING_MATERIAL)
                .unlockedBy(
                    "has_" + plateBlock.getDescriptionId() + "_ingredients",
                    InventoryChangeTrigger.TriggerInstance.hasItems(
                        Arrays.stream(plateBlock.CRAFTING_MATERIAL.getItems())
                            .map(itemStack -> itemStack.getItem())
                            .toArray(Item[]::new)
                    )
                )
                .save(pWriter);
        }

//        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TRAY.get())
//                .pattern("IPI")
//                .define('P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
//                .define('I', Tags.Items.INGOTS_IRON)
//                .unlockedBy(getHasName(ModItems.TRAY.get()), has(ModItems.TRAY.get()))
//                .save(pWriter);

    }
}
