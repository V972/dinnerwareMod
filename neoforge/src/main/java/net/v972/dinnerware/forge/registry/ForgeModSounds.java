package net.v972.dinnerware.forge.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public class ForgeModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, DinnerwareCommon.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TRAY_METAL_LOAD = registerSoundEvent(DinnerwareRegistryNames.Sounds.TRAY_METAL_LOAD);
    public static final DeferredHolder<SoundEvent, SoundEvent> TRAY_METAL_UNLOAD = registerSoundEvent(DinnerwareRegistryNames.Sounds.TRAY_METAL_UNLOAD);

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                DinnerwareConstants.id(name)
        ));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    public static SoundEvent trayMetalLoad() {
        return TRAY_METAL_LOAD.get();
    }

    public static SoundEvent trayMetalUnload() {
        return TRAY_METAL_UNLOAD.get();
    }
}
