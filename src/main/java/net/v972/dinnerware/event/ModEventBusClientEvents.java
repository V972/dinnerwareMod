package net.v972.dinnerware.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.entity.ModBlockEntities;
import net.v972.dinnerware.block.entity.renderer.PlateBlockBlockEntityRenderer;

@Mod.EventBusSubscriber(modid = DinnerwareMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.PLATE_BLOCK_BE.get(), PlateBlockBlockEntityRenderer::new);
    }
}
