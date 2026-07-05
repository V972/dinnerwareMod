package net.v972.dinnerware.forge.config;

import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public final class DinnerwareForgeConfigs {
    private DinnerwareForgeConfigs() {
    }

    public static void register(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, ForgeCommonConfig.SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, ForgeClientConfig.SPEC);
    }
}