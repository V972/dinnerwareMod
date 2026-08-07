package net.v972.dinnerware;

import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.v972.dinnerware.block.DinnerwareBlocks;
import net.v972.dinnerware.dispenser.PlateDispenseItemBehavior;

import java.util.function.Function;

public final class DinnerwareCommon {
    public static final String MOD_ID = DinnerwareConstants.MOD_ID;

    private DinnerwareCommon() {
    }

    public static void init() {
    }

    public static void commonSetup() {
    }

    public static void registerDispenserBehaviors() {
        registerDispenserBehaviors(blockItem -> null);
    }

    public static void registerDispenserBehaviors(Function<BlockItem, DispenseItemBehavior> fallbackProvider) {
        for (Block block : DinnerwareBlocks.getKnownPlateBlocksIterable()) {
            if (!(block.asItem() instanceof BlockItem blockItem)) continue;

            DispenserBlock.registerBehavior(blockItem,
                new PlateDispenseItemBehavior(fallbackProvider.apply(blockItem))
            );
        }
    }
}