package net.v972.dinnerware;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.v972.dinnerware.forge.DinnerwareForgeBootstrap;
import net.v972.dinnerware.screen.ModMenuTypes;
import net.v972.dinnerware.screen.PlateScreen;
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
        modEventBus.addListener(this::commonSetup);
        DinnerwareForgeBootstrap.registerConfigs(context);
        DinnerwareForgeBootstrap.registerForgeEventHandlers(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        DinnerwareCommon.commonSetup();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.PLATE_MENU.get(), PlateScreen::new);
        }
    }
}
