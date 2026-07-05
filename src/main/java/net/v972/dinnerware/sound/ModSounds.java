package net.v972.dinnerware.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DinnerwareCommon.MOD_ID);

    public static final RegistryObject<SoundEvent> TRAY_METAL_LOAD = registerSoundEvent(DinnerwareRegistryNames.Sounds.TRAY_METAL_LOAD);
    public static final RegistryObject<SoundEvent> TRAY_METAL_UNLOAD = registerSoundEvent(DinnerwareRegistryNames.Sounds.TRAY_METAL_UNLOAD);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(DinnerwareCommon.MOD_ID, name)
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
