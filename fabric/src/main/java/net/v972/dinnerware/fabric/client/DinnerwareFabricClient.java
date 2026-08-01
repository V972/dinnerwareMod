package net.v972.dinnerware.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLR;
import net.v972.dinnerware.block.entity.renderer.DinnerwareBEWLRManager;
import net.v972.dinnerware.block.entity.renderer.PlateBlockBlockEntityRenderer;
import net.v972.dinnerware.client.animation.DinnerwareItemAnimations;
import net.v972.dinnerware.client.animation.TrayThirdPersonAnimationProvider;
import net.v972.dinnerware.fabric.client.network.FabricClientNetworkRequests;
import net.v972.dinnerware.fabric.registry.FabricModBlockEntities;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.fabric.registry.FabricModMenuTypes;
import net.v972.dinnerware.screen.PlateScreen;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DinnerwareFabricClient
        implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricClientNetworkRequests.register();

        registerScreens();
        registerItemRenderers();
        registerBlockEntityRenderers();
        registerItemAnimations();
    }

    private static void registerScreens() {
        MenuScreens.register(FabricModMenuTypes.plateMenu(), PlateScreen::new);
    }

    private static void registerItemRenderers() {
        DinnerwareBEWLRManager.register(
            Stream.concat(
                FabricModItems.getKnownPlateItemsSet().stream(),
                FabricModItems.getTrayItemsSet().stream()
            ).collect(Collectors.toSet()),
            DinnerwareBEWLR::new
        );
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRenderers.register(
            FabricModBlockEntities.plateBlockEntityType(),
            PlateBlockBlockEntityRenderer::new
        );
    }

    private static void registerItemAnimations() {
        TrayThirdPersonAnimationProvider provider = new TrayThirdPersonAnimationProvider();

        FabricModItems.getTrayItemsSet().forEach(item ->
            DinnerwareItemAnimations.registerThirdPerson(item, provider)
        );
    }
}