package net.v972.dinnerware.forge.event;

import net.minecraft.world.level.Level;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.platform.DinnerwarePistonHooks;

@EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
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