package net.v972.dinnerware.forge.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.network.packet.ClientboundEatingParticlesPacket;
import net.v972.dinnerware.forge.network.packet.ServerboundCheckTrayAdvancementPacket;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;

import java.util.Optional;

public final class ForgeNetwork {
    private static final String PROTOCOL_VERSION = "2";

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

        CHANNEL.registerMessage(
            messageId++,
            ClientboundEatingParticlesPacket.class,
            ClientboundEatingParticlesPacket::encode,
            ClientboundEatingParticlesPacket::decode,
            ClientboundEatingParticlesPacket::handle,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
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

    public static void broadcastEatingParticles(ServerPlayer player, ItemStack foodStack) {
        CHANNEL.send(
            PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
            new ClientboundEatingParticlesPacket(player.getId(), foodStack)
        );
    }
}