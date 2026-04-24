package net.v972.dinnerware.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.entity.ModBlockEntities;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLR;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLRManager;
import net.v972.dinnerware.block.entity.renderer.PlateBlockBlockEntityRenderer;
import net.v972.dinnerware.item.ModItems;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = DinnerwareMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        // Registers the "DinnerwareBEWLR" to the given item sets
        DinnerwareBEWLRManager.register(
            Stream.concat(ModItems.getPlateItemsSet().stream(), ModItems.getTrayItemsSet().stream()).collect(Collectors.toSet()),
            DinnerwareBEWLR::new
        );
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.PLATE_BLOCK_BE.get(), PlateBlockBlockEntityRenderer::new);
    }
}