package net.v972.dinnerware.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkHooks;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.platform.DinnerwarePlatform;

public final class DinnerwareForgePlatform implements DinnerwarePlatform {
    @Override
    public void openPlateMenu(ServerPlayer player, PlateBlockBlockEntity plateEntity, BlockPos pos) {
        NetworkHooks.openScreen(player, plateEntity, pos);
    }
}