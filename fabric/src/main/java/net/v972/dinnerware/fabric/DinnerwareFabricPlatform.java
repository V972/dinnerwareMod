package net.v972.dinnerware.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.v972.dinnerware.fabric.client.network.FabricClientNetworkRequests;
import net.v972.dinnerware.platform.DinnerwarePlatform;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DinnerwareFabricPlatform implements DinnerwarePlatform {
    @Override
    public void openPlateMenu(ServerPlayer player, MenuProvider menuProvider, BlockPos pos) {
        player.openMenu(new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buffer) {
                buffer.writeBlockPos(pos);
            }

            @Override
            public Component getDisplayName() {
                return menuProvider.getDisplayName();
            }

            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
                return menuProvider.createMenu(
                    containerId,
                    inventory,
                    player
                );
            }
        });
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, Player player) {
        return stack.getItem().getFoodProperties();
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.getItem().hasCraftingRemainingItem();
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        Item remainder = stack.getItem().getCraftingRemainingItem();

        return remainder == null
            ? ItemStack.EMPTY
            : new ItemStack(remainder);
    }

    @Override
    public void requestTrayAdvancementCheck(Player player, TrayAdvancementCheckSource source, ItemStack traySnapshot ) {
        if (!player.level().isClientSide) return;

        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return;
        }

        FabricClientNetworkRequests.requestTrayAdvancementCheck(source, traySnapshot);
    }
}