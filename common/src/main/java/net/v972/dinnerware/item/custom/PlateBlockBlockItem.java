package net.v972.dinnerware.item.custom;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

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

    public int getBurnTimeTicks() {
        return burnTime;
    }
}