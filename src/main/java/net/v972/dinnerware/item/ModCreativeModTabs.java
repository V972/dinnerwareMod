package net.v972.dinnerware.item;

import net.v972.dinnerware.DinnerwareMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.block.ModBlocks;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DinnerwareMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DINNERWARE_TAB = CREATIVE_MODE_TABS.register("dinnerware_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ICON.get()))
                    .title(Component.translatable("creativetab.dinnerware_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.ICON.get());
                        pOutput.accept(ModItems.TRAY.get());

                        pOutput.accept(ModBlocks.PLATE_BLOCK.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
