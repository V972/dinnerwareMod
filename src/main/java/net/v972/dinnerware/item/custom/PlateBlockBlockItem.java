package net.v972.dinnerware.item.custom;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class PlateBlockBlockItem extends BlockItem {
    private final int burnTime;

    public PlateBlockBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        burnTime = 0;
    }

    public PlateBlockBlockItem(Block pBlock, Properties pProperties, int pBurnTimeTicks) {
        super(pBlock, pProperties);
        burnTime = Math.max(pBurnTimeTicks, 0);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }
}