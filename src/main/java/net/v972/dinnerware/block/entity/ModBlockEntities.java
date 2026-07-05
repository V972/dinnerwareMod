package net.v972.dinnerware.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.forge.block.entity.ForgePlateBlockBlockEntity;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DinnerwareCommon.MOD_ID);

    public static final RegistryObject<BlockEntityType<PlateBlockBlockEntity>> PLATE_BLOCK_BE =
        BLOCK_ENTITIES.register(DinnerwareRegistryNames.BlockEntities.PLATE_BLOCK,
            () -> BlockEntityType.Builder.<PlateBlockBlockEntity>of(
                ForgePlateBlockBlockEntity::new,
                ModBlocks.getKnownPlateBlocksArray()
            ).build(null)
        );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static BlockEntityType<PlateBlockBlockEntity> plateBlockEntityType() {
        return PLATE_BLOCK_BE.get();
    }
}
