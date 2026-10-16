package net.v972.dinnerware.fabric.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

public final class FabricNetwork {
    public record TrayAdvancementCheckPayload(TrayAdvancementCheckSource source, ItemStack traySnapshot)
            implements CustomPacketPayload {

        public static final Type<TrayAdvancementCheckPayload> TYPE =
            new Type<>( DinnerwareConstants.id("check_tray_advancement"));

        public static final StreamCodec<RegistryFriendlyByteBuf, TrayAdvancementCheckPayload> STREAM_CODEC =
            StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeEnum(payload.source());
                ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, payload.traySnapshot());
            },
            buffer -> new TrayAdvancementCheckPayload(
                buffer.readEnum(TrayAdvancementCheckSource.class),
                ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer)
            )
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record EatingParticlesPayload(int entityId, ItemStack foodStack)
            implements CustomPacketPayload {

        public static final Type<EatingParticlesPayload> TYPE =
            new Type<>(DinnerwareConstants.id("eating_particles"));

        public static final StreamCodec<RegistryFriendlyByteBuf, EatingParticlesPayload> STREAM_CODEC =
            StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeVarInt(payload.entityId());
                ItemStack.STREAM_CODEC.encode(buffer, payload.foodStack());
            },
            buffer -> new EatingParticlesPayload(
                buffer.readVarInt(),
                ItemStack.STREAM_CODEC.decode(buffer)
            )
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    private static boolean registered;

    private FabricNetwork() {
    }

    public static void register() {
        if (registered) return;

        PayloadTypeRegistry.playC2S().register(
            TrayAdvancementCheckPayload.TYPE,
            TrayAdvancementCheckPayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
            EatingParticlesPayload.TYPE,
            EatingParticlesPayload.STREAM_CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
            TrayAdvancementCheckPayload.TYPE,
            (payload, context) ->
                handleTrayAdvancementCheck(
                    context.player(),
                    payload.source(),
                    payload.traySnapshot()
                )
        );

        registered = true;
    }

    private static void handleTrayAdvancementCheck(ServerPlayer player, TrayAdvancementCheckSource source, ItemStack traySnapshot) {
        if (source == TrayAdvancementCheckSource.CURSOR) {
            checkTray(traySnapshot, player);
            return;
        }

        checkTray(player.containerMenu.getCarried(), player);

        for (Slot slot : player.containerMenu.slots) {
            if (checkTray(slot.getItem(), player)) {
                break;
            }
        }
    }

    private static boolean checkTray(ItemStack stack, ServerPlayer player) {
        if (stack.isEmpty() ||
            stack.getCount() != 1 ||
            !stack.is(ModTags.Items.TRAYS)  ||
            !TrayItem.hasAllItems(stack)) {
            return false;
        }

        TrayItem.checkAndAwardTheOneTrayAdvancement(stack, player);

        return true;
    }

    public static void broadcastEatingParticles(ServerPlayer eater, ItemStack foodStack) {
        Set<ServerPlayer> recipients = new LinkedHashSet<>(PlayerLookup.tracking(eater));
        recipients.add(eater);

        EatingParticlesPayload payload =new EatingParticlesPayload(eater.getId(), foodStack);
        for (ServerPlayer recipient : recipients) {
            ServerPlayNetworking.send(recipient, payload);
        }
    }
}