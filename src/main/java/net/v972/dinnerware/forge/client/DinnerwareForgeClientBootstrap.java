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
import net.v972.dinnerware.forge.registry.ForgeModBlockEntities;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLR;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLRManager;
import net.v972.dinnerware.block.entity.renderer.PlateBlockBlockEntityRenderer;
import net.v972.dinnerware.client.animation.DinnerwareItemAnimations;
import net.v972.dinnerware.client.animation.TrayThirdPersonAnimationProvider;
//import net.v972.dinnerware.client.hud.TrayItemHud;
import net.v972.dinnerware.forge.registry.ForgeModItems;
import net.v972.dinnerware.forge.registry.ForgeModMenuTypes;
import net.v972.dinnerware.screen.PlateScreen;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class DinnerwareForgeClientBootstrap {
    private DinnerwareForgeClientBootstrap() {
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ForgeModMenuTypes.plateMenu(), PlateScreen::new);

        // Registers the "DinnerwareBEWLR" to the given item sets
        DinnerwareBEWLRManager.register(
            Stream.concat(
                ForgeModItems.getKnownPlateItemsSet().stream(),
                ForgeModItems.getTrayItemsSet().stream()
            ).collect(Collectors.toSet()),
            DinnerwareBEWLR::new
        );

        registerItemAnimations();
    }

    private static void registerItemAnimations() {
        TrayThirdPersonAnimationProvider trayAnimationProvider = new TrayThirdPersonAnimationProvider();

        ForgeModItems.getTrayItemsSet().forEach(item ->
            DinnerwareItemAnimations.registerThirdPerson(item.asItem(), trayAnimationProvider)
        );
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ForgeModBlockEntities.plateBlockEntityType(), PlateBlockBlockEntityRenderer::new);
    }

//    @SubscribeEvent
//    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
//        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "tray_overlay",
//            TrayItemHud.getInstance());
//    }
}