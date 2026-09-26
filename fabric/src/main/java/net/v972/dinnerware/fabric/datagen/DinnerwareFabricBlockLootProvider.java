package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Block;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;

public final class DinnerwareFabricBlockLootProvider
        extends FabricBlockLootTableProvider {

    public DinnerwareFabricBlockLootProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public String getName() {
        return "Common Block Loot Tables: dinnerware";
    }

    @Override
    protected void generateBlockLootTables() {
        for (Block block : FabricModBlocks.getKnownPlateBlocksIterable()) {
            add(block, createNameableBlockEntityTable(block));
        }
    }
}