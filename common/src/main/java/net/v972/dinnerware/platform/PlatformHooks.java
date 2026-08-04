package net.v972.dinnerware.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PlatformHooks {
    private static DinnerwarePlatform platform;

    private PlatformHooks() {
    }

    public static void setPlatform(DinnerwarePlatform platform) {
        PlatformHooks.platform = Objects.requireNonNull(platform);
    }

    private static DinnerwarePlatform getPlatform() {
        if (platform == null) {
            throw new IllegalStateException("Dinnerware platform hooks have not been initialized.");
        }

        return platform;
    }


    public static void openPlateMenu(ServerPlayer player, MenuProvider menuProvider, BlockPos pos) {
        getPlatform().openPlateMenu(player, menuProvider, pos);
    }

    public static @Nullable FoodProperties getFoodProperties(
        ItemStack stack, Player player
    ) {
        return getPlatform().getFoodProperties(stack, player);
    }

    public static boolean hasCraftingRemainingItem(ItemStack stack) {
        return getPlatform().hasCraftingRemainingItem(stack);
    }

    public static ItemStack getCraftingRemainingItem(ItemStack stack) {
        return getPlatform().getCraftingRemainingItem(stack);
    }

    public static void requestTrayAdvancementCheck(Player player,
       TrayAdvancementCheckSource source, ItemStack traySnapshot) {
        getPlatform().requestTrayAdvancementCheck(player, source, traySnapshot);
    }

    public static void broadcastEatingParticles(ServerPlayer player, ItemStack foodStack) {
        getPlatform().broadcastEatingParticles(player, foodStack);
    }
}