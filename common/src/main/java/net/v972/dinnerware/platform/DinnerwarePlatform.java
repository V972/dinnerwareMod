package net.v972.dinnerware.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface DinnerwarePlatform {
    void openPlateMenu(ServerPlayer player, MenuProvider menuProvider, BlockPos pos);

    @Nullable
    FoodProperties getFoodProperties(ItemStack stack, Player player);

    boolean hasCraftingRemainingItem(ItemStack stack);

    ItemStack getCraftingRemainingItem(ItemStack stack);

    void requestTrayAdvancementCheck(
        Player player, TrayAdvancementCheckSource source, ItemStack traySnapshot
    );
}