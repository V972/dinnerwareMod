package net.v972.dinnerware;

import com.mojang.logging.LogUtils;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.v972.dinnerware.forge.DinnerwareForgeBootstrap;
import net.v972.dinnerware.forge.DinnerwareForgeLifecycleEvents;
import org.slf4j.Logger;

@Mod(DinnerwareCommon.MOD_ID)
public class DinnerwareMod
{
    public static final String MOD_ID = DinnerwareCommon.MOD_ID;
    private static final Logger LOGGER = LogUtils.getLogger();

    public DinnerwareMod(FMLJavaModLoadingContext context)
    {
        DinnerwareCommon.init();
        IEventBus modEventBus = DinnerwareForgeBootstrap.getModEventBus(context);
        DinnerwareForgeBootstrap.registerRegistries(modEventBus);
        modEventBus.addListener(DinnerwareForgeLifecycleEvents::commonSetup);
        DinnerwareForgeBootstrap.registerConfigs(context);
        DinnerwareForgeBootstrap.registerForgeEventHandlers();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
}
