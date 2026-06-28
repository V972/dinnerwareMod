package net.v972.dinnerware.forge.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.block.entity.ModBlockEntities;
import net.v972.dinnerware.item.ModCreativeModTabs;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.screen.ModMenuTypes;
import net.v972.dinnerware.sound.ModSounds;

public final class DinnerwareForgeRegistries {
    private DinnerwareForgeRegistries() {
    }

    public static void register(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModSounds.register(modEventBus);
    }
}