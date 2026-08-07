package net.v972.dinnerware.fabric.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;
import net.v972.dinnerware.util.ModTags;

import java.util.LinkedHashSet;
import java.util.Set;

public final class FabricNetwork {
    public static final ResourceLocation CHECK_TRAY_ADVANCEMENT = DinnerwareConstants.id("check_tray_advancement");
    public static final ResourceLocation EATING_PARTICLES = DinnerwareConstants.id("eating_particles");

    private static boolean registered;

    private FabricNetwork() {
    }

    public static void register() {
        if (registered) return;

        ServerPlayNetworking.registerGlobalReceiver(
            CHECK_TRAY_ADVANCEMENT,
            (server, player, handler, buffer, responseSender) -> {
                TrayAdvancementCheckSource source = buffer.readEnum(TrayAdvancementCheckSource.class);

                ItemStack traySnapshot = buffer.readItem();

                server.execute(() ->
                    handleTrayAdvancementCheck(
                        player,
                        source,
                        traySnapshot
                    )
                );
            }
        );

        registered = true;
    }

    public static FriendlyByteBuf createTrayAdvancementCheckBuffer(TrayAdvancementCheckSource source, ItemStack traySnapshot) {
        FriendlyByteBuf buffer = PacketByteBufs.create();

        buffer.writeEnum(source);
        buffer.writeItem(traySnapshot);

        return buffer;
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

        for (ServerPlayer recipient : recipients) {
            FriendlyByteBuf buffer = PacketByteBufs.create();
            buffer.writeVarInt(eater.getId());
            buffer.writeItem(foodStack);
            ServerPlayNetworking.send(recipient, EATING_PARTICLES, buffer);
        }
    }
}