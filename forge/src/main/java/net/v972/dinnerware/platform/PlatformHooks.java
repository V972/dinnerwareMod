package net.v972.dinnerware.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;

import java.util.Objects;

public final class PlatformHooks {
    private static DinnerwarePlatform platform;

    private PlatformHooks() {
    }

    public static void setPlatform(DinnerwarePlatform platform) {
        PlatformHooks.platform = Objects.requireNonNull(platform);
    }

    public static void openPlateMenu(ServerPlayer player, PlateBlockBlockEntity plateEntity, BlockPos pos) {
        getPlatform().openPlateMenu(player, plateEntity, pos);
    }

    private static DinnerwarePlatform getPlatform() {
        if (platform == null) {
            throw new IllegalStateException("Dinnerware platform hooks have not been initialized.");
        }

        return platform;
    }
}