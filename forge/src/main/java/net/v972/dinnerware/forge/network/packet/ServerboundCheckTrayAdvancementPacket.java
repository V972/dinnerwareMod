package net.v972.dinnerware.forge.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;
import net.v972.dinnerware.util.ModTags;

import java.util.function.Supplier;

public final class ServerboundCheckTrayAdvancementPacket {
    private final TrayAdvancementCheckSource source;
    private final ItemStack traySnapshot;

    public ServerboundCheckTrayAdvancementPacket(
            TrayAdvancementCheckSource source,
            ItemStack traySnapshot
    ) {
        this.source = source;
        this.traySnapshot = traySnapshot.copy();
    }

    public static void encode(ServerboundCheckTrayAdvancementPacket packet,
                              FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.source);
        buffer.writeItem(packet.traySnapshot);
    }

    public static ServerboundCheckTrayAdvancementPacket decode(FriendlyByteBuf buffer) {
        return new ServerboundCheckTrayAdvancementPacket(
                buffer.readEnum(TrayAdvancementCheckSource.class),
                buffer.readItem()
        );
    }

    public static void handle(ServerboundCheckTrayAdvancementPacket packet,
                              Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            if (packet.source == TrayAdvancementCheckSource.CURSOR) {
                checkTray(packet.traySnapshot, player);
                return;
            }

            checkTray(player.containerMenu.getCarried(), player);

            for (Slot slot : player.containerMenu.slots) {
                if (checkTray(slot.getItem(), player)) {
                    break;
                }
            }
        });

        context.setPacketHandled(true);
    }

    private static boolean checkTray(
            ItemStack stack,
            ServerPlayer player
    ) {
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