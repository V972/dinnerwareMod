package net.v972.dinnerware.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public final class DinnerwareBlockEntities {
    private static Supplier<BlockEntityType<PlateBlockBlockEntity>> plateBlockEntityTypeSupplier;

    private DinnerwareBlockEntities() {
    }

    public static void setPlateBlockEntityTypeSupplier(Supplier<BlockEntityType<PlateBlockBlockEntity>> supplier) {
        plateBlockEntityTypeSupplier = supplier;
    }

    public static BlockEntityType<PlateBlockBlockEntity> plateBlockEntityType() {
        if (plateBlockEntityTypeSupplier == null) {
            throw new IllegalStateException("Dinnerware block entity types have not been initialized");
        }

        return plateBlockEntityTypeSupplier.get();
    }
}