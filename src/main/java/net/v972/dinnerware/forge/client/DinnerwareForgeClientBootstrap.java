package net.v972.dinnerware.forge.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
//import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
//import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.block.entity.ModBlockEntities;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLR;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLRManager;
import net.v972.dinnerware.block.entity.renderer.PlateBlockBlockEntityRenderer;
//import net.v972.dinnerware.client.hud.TrayItemHud;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.screen.ModMenuTypes;
import net.v972.dinnerware.screen.PlateScreen;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class DinnerwareForgeClientBootstrap {
    private DinnerwareForgeClientBootstrap() {
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.PLATE_MENU.get(), PlateScreen::new);

        // Registers the "DinnerwareBEWLR" to the given item sets
        DinnerwareBEWLRManager.register(
                Stream.concat(
                        ModItems.getKnownPlateItemsSet().stream(),
                        ModItems.getTrayItemsSet().stream()
                ).collect(Collectors.toSet()),
                DinnerwareBEWLR::new
        );
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.PLATE_BLOCK_BE.get(), PlateBlockBlockEntityRenderer::new);
    }

//    @SubscribeEvent
//    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
//        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "tray_overlay",
//            TrayItemHud.getInstance());
//    }
}