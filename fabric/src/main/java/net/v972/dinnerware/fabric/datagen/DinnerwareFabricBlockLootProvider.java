package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Block;
import net.v972.dinnerware.datagen.DinnerwareDatagenPaths;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;

public final class DinnerwareFabricBlockLootProvider extends FabricBlockLootTableProvider {

    public DinnerwareFabricBlockLootProvider(FabricDataOutput output) {
        super(
            new FabricDataOutput(
                output.getModContainer(),
                DinnerwareDatagenPaths.commonOutput(),
                output.isStrictValidationEnabled()
            )
        );
    }

    @Override
    public String getName() {
        return "Common Block Loot Tables: dinnerware";
    }

    @Override
    public void generate() {
        for (Block block : FabricModBlocks.getKnownPlateBlocksIterable()) {
            add(block, createNameableBlockEntityTable(block));
        }
    }
}