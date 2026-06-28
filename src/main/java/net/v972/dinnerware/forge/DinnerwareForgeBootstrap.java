package net.v972.dinnerware.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.block.entity.ModBlockEntities;
import net.v972.dinnerware.config.ClientConfig;
import net.v972.dinnerware.config.CommonConfig;
import net.v972.dinnerware.item.ModCreativeModTabs;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.screen.ModMenuTypes;
import net.v972.dinnerware.sound.ModSounds;

public final class DinnerwareForgeBootstrap {
    private DinnerwareForgeBootstrap() {
    }

    public static IEventBus getModEventBus(FMLJavaModLoadingContext context) {
        return context.getModEventBus();
    }

    public static void registerRegistries(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModSounds.register(modEventBus);
    }

    public static void registerConfigs(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    public static void registerForgeEventHandlers() {
        MinecraftForge.EVENT_BUS.register(DinnerwareForgeLifecycleEvents.class);
    }
}