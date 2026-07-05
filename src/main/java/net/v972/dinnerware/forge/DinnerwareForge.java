package net.v972.dinnerware.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.v972.dinnerware.DinnerwareCommon;

@Mod(DinnerwareCommon.MOD_ID)
public class DinnerwareForge
{
    public DinnerwareForge(FMLJavaModLoadingContext context)
    {
        DinnerwareForgeBootstrap.registerPlatformHooks();

        DinnerwareCommon.init();

        IEventBus modEventBus = DinnerwareForgeBootstrap.getModEventBus(context);

        DinnerwareForgeBootstrap.registerRegistries(modEventBus);
        DinnerwareForgeBootstrap.registerModEventHandlers(modEventBus);
        DinnerwareForgeBootstrap.registerConfigs(context);
        DinnerwareForgeBootstrap.registerForgeEventHandlers();
    }
}
