package net.v972.dinnerware.forge.event;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.platform.DinnerwarePistonHooks;

@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DinnerwareForgePistonEvents {
    private DinnerwareForgePistonEvents() {
    }

    @SubscribeEvent
    public static void onPistonPre(PistonEvent.Pre event) {
        DinnerwarePistonHooks.beforePistonMove(
            (Level) event.getLevel(),
            event.getPos(),
            event.getDirection()
        );
    }
}