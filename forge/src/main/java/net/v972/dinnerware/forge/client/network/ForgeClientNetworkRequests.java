package net.v972.dinnerware.forge.client.network;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.network.ForgeNetwork;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;

@Mod.EventBusSubscriber(
    modid = DinnerwareConstants.MOD_ID,
    value = Dist.CLIENT,
    bus = Mod.EventBusSubscriber.Bus.FORGE
)
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
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START || pendingSource == null) {
            return;
        }

        TrayAdvancementCheckSource source = pendingSource;
        ItemStack snapshot = pendingTraySnapshot;

        pendingSource = null;
        pendingTraySnapshot = ItemStack.EMPTY;

        ForgeNetwork.requestTrayAdvancementCheck(source, snapshot);
    }
}