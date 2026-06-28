package net.v972.dinnerware.forge.config;

import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.v972.dinnerware.config.ClientConfig;
import net.v972.dinnerware.config.CommonConfig;

public final class DinnerwareForgeConfigs {
    private DinnerwareForgeConfigs() {
    }

    public static void register(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}