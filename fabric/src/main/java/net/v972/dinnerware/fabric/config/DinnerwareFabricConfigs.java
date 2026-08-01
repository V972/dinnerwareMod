package net.v972.dinnerware.fabric.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.world.InteractionResult;

public final class DinnerwareFabricConfigs {
    private static boolean registered;

    private DinnerwareFabricConfigs() {
    }

    public static void register() {
        if (registered) return;

        AutoConfig.register(FabricCommonConfig.class, JanksonConfigSerializer::new);
        AutoConfig.register(FabricClientConfig.class, JanksonConfigSerializer::new);

        ConfigHolder<FabricCommonConfig> commonHolder =
            AutoConfig.getConfigHolder(FabricCommonConfig.class);

        commonHolder.registerSaveListener(
            (holder, config) -> {
                config.sanitizeFoodBlacklist();
                return InteractionResult.PASS;
        });

        if (commonHolder.getConfig().sanitizeFoodBlacklist()) {
            commonHolder.save();
        }

        registered = true;
    }

    // -----------------------------

    public static FabricCommonConfig common() {
        ensureRegistered();

        return AutoConfig.getConfigHolder(FabricCommonConfig.class)
            .getConfig();
    }

    public static FabricClientConfig client() {
        ensureRegistered();

        return AutoConfig.getConfigHolder(FabricClientConfig.class)
            .getConfig();
    }

    // -----------------------------

    private static void ensureRegistered() {
        if (!registered) {
            throw new IllegalStateException(
                "Fabric configs have not been registered"
            );
        }
    }

    public static void reloadCommon() {
        ensureRegistered();
        AutoConfig
            .getConfigHolder(FabricCommonConfig.class)
            .load();
    }

    public static void reloadClient() {
        ensureRegistered();
        AutoConfig
            .getConfigHolder(FabricClientConfig.class)
            .load();
    }
}