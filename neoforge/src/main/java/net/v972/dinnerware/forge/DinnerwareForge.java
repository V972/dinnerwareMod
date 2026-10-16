package net.v972.dinnerware.forge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.v972.dinnerware.DinnerwareCommon;

@Mod(DinnerwareCommon.MOD_ID)
public class DinnerwareForge
{
    public DinnerwareForge(IEventBus modEventBus, ModContainer modContainer)
    {
        DinnerwareForgeBootstrap.registerPlatformHooks();

        DinnerwareCommon.init();

        DinnerwareForgeBootstrap.registerRegistries(modEventBus);
        DinnerwareForgeBootstrap.registerModEventHandlers(modEventBus);
        DinnerwareForgeBootstrap.registerConfigs(modContainer);
    }
}
