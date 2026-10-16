package net.v972.dinnerware.forge.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.forge.block.entity.ForgePlateBlockBlockEntity;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public class ForgeModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DinnerwareCommon.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlateBlockBlockEntity>> PLATE_BLOCK_BE =
        BLOCK_ENTITIES.register(DinnerwareRegistryNames.BlockEntities.PLATE_BLOCK,
            () -> BlockEntityType.Builder.<PlateBlockBlockEntity>of(
                ForgePlateBlockBlockEntity::new,
                ForgeModBlocks.getKnownPlateBlocksArray()
            ).build(null)
        );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static BlockEntityType<PlateBlockBlockEntity> plateBlockEntityType() {
        return PLATE_BLOCK_BE.get();
    }
}
