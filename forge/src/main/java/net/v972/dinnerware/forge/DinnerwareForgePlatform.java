package net.v972.dinnerware.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.forge.client.network.ForgeClientNetworkRequests;
import net.v972.dinnerware.platform.DinnerwarePlatform;
import net.v972.dinnerware.platform.TrayAdvancementCheckSource;
import org.jetbrains.annotations.Nullable;

public final class DinnerwareForgePlatform implements DinnerwarePlatform {
    @Override
    public void openPlateMenu(ServerPlayer player, MenuProvider menuProvider, BlockPos pos) {
        NetworkHooks.openScreen(player, menuProvider, pos);
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(
            ItemStack stack,
            Player player
    ) {
        return stack.getFoodProperties(player);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.hasCraftingRemainingItem();
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.getCraftingRemainingItem();
    }

    @Override
    public void requestTrayAdvancementCheck(Player player, TrayAdvancementCheckSource source, ItemStack traySnapshot) {
        if (player.level().isClientSide) {
            ForgeClientNetworkRequests.requestTrayAdvancementCheck(source, traySnapshot);
        }
    }
}