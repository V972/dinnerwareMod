package net.v972.dinnerware.forge;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.advancement.ModCriterionTriggers;
import net.v972.dinnerware.forge.registry.ForgeModCriterionTriggers;
import net.v972.dinnerware.mixins.DispenserBlockAccessor;

public final class DinnerwareForgeLifecycleEvents {
    private DinnerwareForgeLifecycleEvents() {
    }

    public static void commonSetup(final FMLCommonSetupEvent event) {
        DinnerwareCommon.commonSetup();
        ForgeModCriterionTriggers.register();

        ModCriterionTriggers.setManualTriggerSupplier(
                ForgeModCriterionTriggers::manualTrigger
        );
    }

    public static void loadComplete(final FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> DinnerwareCommon.registerDispenserBehaviors(
            blockItem -> DispenserBlockAccessor.dinnerware$getDispenserRegistry().get(blockItem)
        ));
    }

    @SubscribeEvent
    public static void onServerStarting(final ServerStartingEvent event) {

    }
}