package net.v972.dinnerware.forge.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.screen.ModMenuTypes;
import net.v972.dinnerware.screen.PlateScreen;

@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class DinnerwareForgeClientBootstrap {
    private DinnerwareForgeClientBootstrap() {
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.PLATE_MENU.get(), PlateScreen::new);
    }
}