package net.v972.dinnerware.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.util.ModTags;

public final class DinnerwarePistonHooks {
    private DinnerwarePistonHooks() {
    }

    public static void beforePistonMove(Level level, BlockPos pistonPos, Direction direction) {

        if (level.isClientSide) return;

        for (int distance = 1; distance <= 12; distance++) {
            BlockPos softBreakerPos = pistonPos.relative(direction, distance);

            if (!level.getBlockState(softBreakerPos).is(ModTags.Blocks.PLATE_SOFT_BREAKERS)) {
                continue;
            }

            BlockPos platePos = softBreakerPos.relative(direction);
            if (level.getBlockEntity(platePos)
                instanceof PlateBlockBlockEntity plateEntity
                && !plateEntity.isEmpty()) {

                PlateBlock.dropPlateStackWithContents(plateEntity, level, platePos);
                level.removeBlock(platePos, false);
                return;
            }
        }
    }
}