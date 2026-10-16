package net.v972.dinnerware.forge.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.client.network.ForgeClientPacketHandlers;
import org.jetbrains.annotations.NotNull;

public record ClientboundEatingParticlesPacket(int entityId, ItemStack foodStack)
    implements CustomPacketPayload {

    public static final Type<ClientboundEatingParticlesPacket> TYPE =
        new Type<>(DinnerwareConstants.id("eating_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundEatingParticlesPacket> STREAM_CODEC =
        StreamCodec.of(
        (buffer, packet) -> {
            buffer.writeVarInt(packet.entityId());
            ItemStack.STREAM_CODEC.encode(buffer, packet.foodStack());
        },
        buffer -> new ClientboundEatingParticlesPacket(
            buffer.readVarInt(),
            ItemStack.STREAM_CODEC.decode(buffer)
        )
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientboundEatingParticlesPacket packet, IPayloadContext context) {
        ForgeClientPacketHandlers.handleEatingParticles(packet.entityId(), packet.foodStack());
    }
}