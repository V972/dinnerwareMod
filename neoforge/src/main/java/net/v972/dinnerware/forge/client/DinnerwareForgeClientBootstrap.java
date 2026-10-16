package net.v972.dinnerware.forge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
//import net.minecraft.client.gui.LayeredDraw;
//import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.v972.dinnerware.DinnerwareCommon;
//import net.v972.dinnerware.forge.client.hud.ForgeTrayItemHud;
import net.v972.dinnerware.forge.registry.ForgeModBlockEntities;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLR;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLRManager;
import net.v972.dinnerware.block.entity.renderer.PlateBlockBlockEntityRenderer;
import net.v972.dinnerware.client.animation.DinnerwareItemAnimations;
import net.v972.dinnerware.client.animation.TrayThirdPersonAnimationProvider;
import net.v972.dinnerware.forge.registry.ForgeModItems;
import net.v972.dinnerware.forge.registry.ForgeModMenuTypes;
import net.v972.dinnerware.screen.PlateScreen;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class DinnerwareForgeClientBootstrap {
    private DinnerwareForgeClientBootstrap() {
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
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

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ForgeModMenuTypes.plateMenu(), PlateScreen::new);
    }

//    @SubscribeEvent
//    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
//        event.registerAboveAll(
//            DinnerwareConstants.id("tray_overlay"),
//            (guiGraphics, deltaTracker) ->
//                ForgeTrayItemHud.getInstance().render(
//                    guiGraphics,
//                    deltaTracker.getGameTimeDeltaPartialTick(false)
//                )
//        );
//    }
}