package net.v972.dinnerware.forge;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.v972.dinnerware.DinnerwareCommon;

public final class DinnerwareForgeLifecycleEvents {
    private DinnerwareForgeLifecycleEvents() {
    }

    public static void commonSetup(final FMLCommonSetupEvent event) {
        DinnerwareCommon.commonSetup();
    }

    @SubscribeEvent
    public static void onServerStarting(final ServerStartingEvent event) {

    }
}