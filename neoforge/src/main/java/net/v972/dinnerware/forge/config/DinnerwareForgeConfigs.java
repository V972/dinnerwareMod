package net.v972.dinnerware.forge.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

public final class DinnerwareForgeConfigs {
    private DinnerwareForgeConfigs() {
    }

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ForgeCommonConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ForgeClientConfig.SPEC);
    }
}