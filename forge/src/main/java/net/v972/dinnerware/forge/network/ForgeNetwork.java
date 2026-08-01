package net.v972.dinnerware.forge.network;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.network.packet.ServerboundCheckTrayAdvancementPacket;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;

public final class ForgeNetwork {
    private static final String PROTOCOL_VERSION = "1";

    private static final SimpleChannel CHANNEL =
        NetworkRegistry.newSimpleChannel(
            DinnerwareConstants.id("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
        );

    private ForgeNetwork() {
    }

    public static void register() {
        int messageId = 0;

        CHANNEL.registerMessage(
            messageId++,
            ServerboundCheckTrayAdvancementPacket.class,
            ServerboundCheckTrayAdvancementPacket::encode,
            ServerboundCheckTrayAdvancementPacket::decode,
            ServerboundCheckTrayAdvancementPacket::handle
        );
    }

    public static void requestTrayAdvancementCheck(
        TrayAdvancementCheckSource source,
        ItemStack traySnapshot
    ) {
        CHANNEL.sendToServer(
            new ServerboundCheckTrayAdvancementPacket(source, traySnapshot)
        );
    }
}