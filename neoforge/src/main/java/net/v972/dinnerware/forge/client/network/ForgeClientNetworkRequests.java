package net.v972.dinnerware.forge.client.network;

import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.network.ForgeNetwork;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;

@EventBusSubscriber(modid = DinnerwareConstants.MOD_ID, value = Dist.CLIENT)
public final class ForgeClientNetworkRequests {
    private static TrayAdvancementCheckSource pendingSource;
    private static ItemStack pendingTraySnapshot = ItemStack.EMPTY;

    private ForgeClientNetworkRequests() {}

    public static void requestTrayAdvancementCheck(
        TrayAdvancementCheckSource source,
        ItemStack traySnapshot
    ) {
        pendingSource = source;
        pendingTraySnapshot = traySnapshot.copy();
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (pendingSource == null) {
            return;
        }

        TrayAdvancementCheckSource source = pendingSource;
        ItemStack snapshot = pendingTraySnapshot;

        pendingSource = null;
        pendingTraySnapshot = ItemStack.EMPTY;

        ForgeNetwork.requestTrayAdvancementCheck(source, snapshot);
    }
}