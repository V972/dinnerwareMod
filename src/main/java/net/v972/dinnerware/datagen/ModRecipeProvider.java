package net.v972.dinnerware.datagen;

import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModBlocks.PLATE_BLOCK.get())
                .pattern("Q Q")
                .pattern(" Q ")
                .define('Q', Tags.Items.GEMS_QUARTZ)
                .unlockedBy(getHasName(ModBlocks.PLATE_BLOCK.get()), has(ModBlocks.PLATE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TRAY.get())
                .pattern("IPI")
                .define('P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(ModItems.TRAY.get()), has(ModItems.TRAY.get()))
                .save(pWriter);
    }
}
