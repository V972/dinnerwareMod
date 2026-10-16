package net.v972.dinnerware.forge.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;
import net.v972.dinnerware.util.ModTags;

public record ServerboundCheckTrayAdvancementPacket(TrayAdvancementCheckSource source, ItemStack traySnapshot)
    implements CustomPacketPayload {

    public static final Type<ServerboundCheckTrayAdvancementPacket> TYPE =
        new Type<>(DinnerwareConstants.id("check_tray_advancement"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundCheckTrayAdvancementPacket> STREAM_CODEC =
        StreamCodec.of(
        (buffer, packet) -> {
            buffer.writeEnum(packet.source());
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, packet.traySnapshot());
        },
        buffer -> new ServerboundCheckTrayAdvancementPacket(
            buffer.readEnum(TrayAdvancementCheckSource.class),
            ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer)
        )
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerboundCheckTrayAdvancementPacket packet, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        if (packet.source() == TrayAdvancementCheckSource.CURSOR) {
            checkTray(packet.traySnapshot(), player);
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
            !stack.is(ModTags.Items.TRAYS) ||
            !TrayItem.hasAllItems(stack)) {

            return false;
        }

        TrayItem.checkAndAwardTheOneTrayAdvancement(stack, player);

        return true;
    }
}