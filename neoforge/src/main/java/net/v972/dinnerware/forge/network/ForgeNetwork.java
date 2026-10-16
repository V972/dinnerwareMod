package net.v972.dinnerware.forge.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.v972.dinnerware.forge.network.packet.ClientboundEatingParticlesPacket;
import net.v972.dinnerware.forge.network.packet.ServerboundCheckTrayAdvancementPacket;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;

public final class ForgeNetwork {
    private static final String PROTOCOL_VERSION = "2";

    private ForgeNetwork() {}

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        registrar.playToServer(
            ServerboundCheckTrayAdvancementPacket.TYPE,
            ServerboundCheckTrayAdvancementPacket.STREAM_CODEC,
            ServerboundCheckTrayAdvancementPacket::handle
        );

        registrar.playToClient(
            ClientboundEatingParticlesPacket.TYPE,
            ClientboundEatingParticlesPacket.STREAM_CODEC,
            ClientboundEatingParticlesPacket::handle
        );
    }

    public static void requestTrayAdvancementCheck(TrayAdvancementCheckSource source, ItemStack traySnapshot) {
        PacketDistributor.sendToServer(
            new ServerboundCheckTrayAdvancementPacket(source, traySnapshot)
        );
    }

    public static void broadcastEatingParticles(ServerPlayer player, ItemStack foodStack) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(
            player,
            new ClientboundEatingParticlesPacket(player.getId(), foodStack)
        );
    }
}