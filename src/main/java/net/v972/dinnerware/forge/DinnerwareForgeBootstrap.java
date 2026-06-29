package net.v972.dinnerware.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.v972.dinnerware.forge.config.DinnerwareForgeConfigs;
import net.v972.dinnerware.forge.registry.DinnerwareForgeRegistries;
import net.v972.dinnerware.platform.PlatformHooks;

public final class DinnerwareForgeBootstrap {
    private DinnerwareForgeBootstrap() {
    }

    public static IEventBus getModEventBus(FMLJavaModLoadingContext context) {
        return context.getModEventBus();
    }

    public static void registerRegistries(IEventBus modEventBus) {
        DinnerwareForgeRegistries.register(modEventBus);
    }

    public static void registerConfigs(FMLJavaModLoadingContext context) {
        DinnerwareForgeConfigs.register(context);
    }

    public static void registerForgeEventHandlers() {
        MinecraftForge.EVENT_BUS.register(DinnerwareForgeLifecycleEvents.class);
    }

    public static void registerModEventHandlers(IEventBus modEventBus) {
        modEventBus.addListener(DinnerwareForgeLifecycleEvents::commonSetup);
    }

    // ========================================

    public static void registerPlatformHooks() {
        PlatformHooks.setPlatform(new DinnerwareForgePlatform());
    }
}