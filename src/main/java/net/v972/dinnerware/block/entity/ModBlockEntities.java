package net.v972.dinnerware.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DinnerwareMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<PlateBlockBlockEntity>> PLATE_BLOCK_BE =
        BLOCK_ENTITIES.register(
            "plate_block",
            () -> BlockEntityType.Builder.of(PlateBlockBlockEntity::new, ModBlocks.getKnownBlocksArray()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
