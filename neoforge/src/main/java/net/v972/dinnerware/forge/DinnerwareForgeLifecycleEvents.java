package net.v972.dinnerware.forge;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.advancement.ModCriterionTriggers;
import net.v972.dinnerware.forge.block.entity.ForgePlateBlockBlockEntity;
import net.v972.dinnerware.forge.network.ForgeNetwork;
import net.v972.dinnerware.forge.registry.ForgeModBlockEntities;
import net.v972.dinnerware.forge.registry.ForgeModCriterionTriggers;
import net.v972.dinnerware.mixins.DispenserBlockAccessor;

public final class DinnerwareForgeLifecycleEvents {
    private DinnerwareForgeLifecycleEvents() {
    }

    public static void commonSetup(final FMLCommonSetupEvent event) {
        DinnerwareCommon.commonSetup();

        ModCriterionTriggers.setManualTriggerSupplier(
            ForgeModCriterionTriggers::manualTrigger
        );
    }

    public static void loadComplete(final FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> DinnerwareCommon.registerDispenserBehaviors(
            blockItem -> DispenserBlockAccessor.dinnerware$getDispenserRegistry().get(blockItem)
        ));
    }

    public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            ForgeModBlockEntities.PLATE_BLOCK_BE.get(),
            (blockEntity, side) ->
                ((ForgePlateBlockBlockEntity) blockEntity).getItemHandler()
        );
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        ForgeNetwork.register(event);
    }
}