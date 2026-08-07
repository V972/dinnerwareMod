package net.v972.dinnerware.item.context;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public final class DinnerwareUseOnContext extends UseOnContext {
    public DinnerwareUseOnContext(
            Level level,
            Player player,
            InteractionHand hand,
            ItemStack stack,
            BlockHitResult hitResult
    ) {
        super(level, player, hand, stack, hitResult);
    }
}