package net.v972.dinnerware;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.dispenser.PlateDispenseItemBehavior;

public final class DinnerwareCommon {
    public static final String MOD_ID = "dinnerware";

    private DinnerwareCommon() {
    }

    public static void init() {
    }

    public static void commonSetup() {
    }

    public static void registerDispenserBehaviors() {
        for (Block block : ModBlocks.getKnownPlateBlocksIterable()) {
            if (!(block.asItem() instanceof BlockItem blockItem)) continue;
            DispenserBlock.registerBehavior(blockItem, new PlateDispenseItemBehavior());
        }
    }
}