package net.v972.dinnerware.block;

import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public final class DinnerwareBlocks {
    private static Supplier<Iterable<Block>> knownPlateBlocksIterableSupplier;
    private static Supplier<Block[]> knownPlateBlocksArraySupplier;

    private DinnerwareBlocks() {
    }

    public static void setKnownPlateBlocksIterableSupplier(Supplier<Iterable<Block>> supplier) {
        knownPlateBlocksIterableSupplier = supplier;
    }

    public static void setKnownPlateBlocksArraySupplier(Supplier<Block[]> supplier) {
        knownPlateBlocksArraySupplier = supplier;
    }

    public static Iterable<Block> getKnownPlateBlocksIterable() {
        if (knownPlateBlocksIterableSupplier == null) {
            throw new IllegalStateException("Dinnerware blocks have not been initialized");
        }

        return knownPlateBlocksIterableSupplier.get();
    }

    public static Block[] getKnownPlateBlocksArray() {
        if (knownPlateBlocksArraySupplier == null) {
            throw new IllegalStateException("Dinnerware blocks have not been initialized");
        }

        return knownPlateBlocksArraySupplier.get();
    }
}