package net.v972.dinnerware.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;

public interface DinnerwarePlatform {
    void openPlateMenu(ServerPlayer player, PlateBlockBlockEntity plateEntity, BlockPos pos);
}