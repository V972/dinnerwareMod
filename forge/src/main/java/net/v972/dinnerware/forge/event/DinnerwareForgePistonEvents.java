package net.v972.dinnerware.forge.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.util.ModTags;

@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DinnerwareForgePistonEvents {
    private DinnerwareForgePistonEvents() {
    }

    @SubscribeEvent
    public static void onPistonPre(PistonEvent.Pre event) {
        Level level = (Level) event.getLevel();

        if (level.isClientSide) {
            return;
        }

        Direction direction = event.getDirection();
        BlockPos pistonPos = event.getPos();

        for (int distance = 1; distance <= 12; distance++) {
            BlockPos softBreakerPos = pistonPos.relative(direction, distance);

            if (!level.getBlockState(softBreakerPos).is(ModTags.Blocks.PLATE_SOFT_BREAKERS)) {
                continue;
            }

            BlockPos platePos = softBreakerPos.relative(direction);

            if (level.getBlockEntity(platePos) instanceof PlateBlockBlockEntity plateEntity &&
                    !plateEntity.isEmpty()) {
                PlateBlock.dropPlateStackWithContents(plateEntity, level, platePos);
                level.removeBlock(platePos, false);
                return;
            }
        }
    }
}