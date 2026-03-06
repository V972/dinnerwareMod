package net.v972.dinnerware.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.v972.dinnerware.block.ModBlocks;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for(Block block : ModBlocks.getKnownBlocks()) {
            this.add(block, this::createNameableBlockEntityTable);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.getKnownBlocks();
    }
}
