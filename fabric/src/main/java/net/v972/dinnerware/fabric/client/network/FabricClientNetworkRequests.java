package net.v972.dinnerware.fabric.client.network;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.client.network.EatingParticleClientHandler;
import net.v972.dinnerware.fabric.network.FabricNetwork;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;

public final class FabricClientNetworkRequests {
    private static TrayAdvancementCheckSource pendingSource;
    private static ItemStack pendingTraySnapshot = ItemStack.EMPTY;

    private static boolean registered;

    private FabricClientNetworkRequests() {
    }

    public static void register() {
        if (registered) return;

        ClientTickEvents.START_CLIENT_TICK.register(client ->
            sendPendingTrayAdvancementCheck()
        );

        ClientPlayNetworking.registerGlobalReceiver(
            FabricNetwork.EatingParticlesPayload.TYPE,
            (payload, context) ->
                EatingParticleClientHandler.handle(
                    payload.entityId(),
                    payload.foodStack()
                )
        );

        registered = true;
    }

    public static void requestTrayAdvancementCheck(TrayAdvancementCheckSource source, ItemStack traySnapshot) {
        pendingSource = source;
        pendingTraySnapshot = traySnapshot.copy();
    }

    private static void sendPendingTrayAdvancementCheck() {
        if (pendingSource == null) return;

        TrayAdvancementCheckSource source = pendingSource;
        ItemStack snapshot = pendingTraySnapshot;

        pendingSource = null;
        pendingTraySnapshot = ItemStack.EMPTY;

        if (!ClientPlayNetworking.canSend(FabricNetwork.TrayAdvancementCheckPayload.TYPE)) {
            return;
        }

        ClientPlayNetworking.send(new FabricNetwork.TrayAdvancementCheckPayload(source, snapshot));
    }
}