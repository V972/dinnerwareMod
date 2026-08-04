package net.v972.dinnerware.forge.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.v972.dinnerware.forge.client.network.ForgeClientPacketHandlers;

import java.util.function.Supplier;

public final class ClientboundEatingParticlesPacket {
    private final int entityId;
    private final ItemStack foodStack;

    public ClientboundEatingParticlesPacket(int entityId, ItemStack foodStack) {
        this.entityId = entityId;
        this.foodStack = foodStack.copy();
    }

    public static void encode(ClientboundEatingParticlesPacket packet, FriendlyByteBuf buffer) {
        buffer.writeVarInt(packet.entityId);
        buffer.writeItem(packet.foodStack);
    }

    public static ClientboundEatingParticlesPacket decode(FriendlyByteBuf buffer) {
        return new ClientboundEatingParticlesPacket(buffer.readVarInt(), buffer.readItem());
    }

    public static void handle(ClientboundEatingParticlesPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(
            Dist.CLIENT,
            () -> () -> ForgeClientPacketHandlers.handleEatingParticles(packet.entityId, packet.foodStack)
        ));

        context.setPacketHandled(true);
    }
}