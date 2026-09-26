package net.v972.dinnerware.datagen.loot;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.v972.dinnerware.forge.registry.ForgeModBlocks;
import org.jetbrains.annotations.NotNull;

public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        for (Block block : ForgeModBlocks.getKnownPlateBlocksIterable()) {
            this.add(block, blockToDrop -> createNameableBlockEntityTable(blockToDrop));
        }
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ForgeModBlocks.getKnownPlateBlocksIterable();
    }
}