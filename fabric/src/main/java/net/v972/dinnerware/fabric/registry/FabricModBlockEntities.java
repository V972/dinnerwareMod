package net.v972.dinnerware.fabric.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public final class FabricModBlockEntities {
    private static BlockEntityType<PlateBlockBlockEntity> plateBlockEntityType;

    private static boolean registered;

    private FabricModBlockEntities() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        plateBlockEntityType = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            DinnerwareConstants.id(DinnerwareRegistryNames.BlockEntities.PLATE_BLOCK),
            BlockEntityType.Builder.of(
                PlateBlockBlockEntity::new,
                FabricModBlocks.getKnownPlateBlocksArray()
            ).build(null)
        );

        registered = true;
    }

    public static BlockEntityType<PlateBlockBlockEntity> plateBlockEntityType() {
        if (!registered) {
            throw new IllegalStateException(
                "Fabric block entities have not been registered"
            );
        }

        return plateBlockEntityType;
    }
}