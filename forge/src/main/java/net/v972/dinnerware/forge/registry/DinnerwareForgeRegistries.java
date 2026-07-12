package net.v972.dinnerware.forge.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public final class DinnerwareForgeRegistries {
    private DinnerwareForgeRegistries() {
    }

    public static void register(IEventBus modEventBus) {
        ForgeModItems.register(modEventBus);
        ForgeModBlocks.register(modEventBus);
        ForgeModCreativeModTabs.register(modEventBus);
        ForgeModBlockEntities.register(modEventBus);
        ForgeModMenuTypes.register(modEventBus);
        ForgeModSounds.register(modEventBus);
    }
}