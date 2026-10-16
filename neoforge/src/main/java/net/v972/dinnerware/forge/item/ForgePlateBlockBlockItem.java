package net.v972.dinnerware.forge.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import org.jetbrains.annotations.Nullable;

public final class ForgePlateBlockBlockItem extends PlateBlockBlockItem {
    public ForgePlateBlockBlockItem(
            Block block,
            Properties properties
    ) {
        super(block, properties);
    }

    public ForgePlateBlockBlockItem(
            Block block,
            Properties properties,
            int burnTimeTicks
    ) {
        super(block, properties, burnTimeTicks);
    }

    @Override
    public int getBurnTime(
            ItemStack stack,
            @Nullable RecipeType<?> recipeType
    ) {
        return getBurnTimeTicks();
    }
}