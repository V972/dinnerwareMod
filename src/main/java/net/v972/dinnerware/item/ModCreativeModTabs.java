package net.v972.dinnerware.item;

import net.minecraft.world.level.block.Block;
import net.v972.dinnerware.DinnerwareCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.block.ModBlocks;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DinnerwareCommon.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DINNERWARE_TAB = CREATIVE_MODE_TABS.register(
            DinnerwareRegistryNames.CreativeTabs.DINNERWARE_TAB,
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.icon()))
            .title(Component.translatable("creativetab.dinnerware_tab"))
            .displayItems((pParameters, pOutput) -> {
                for(Block block : ModBlocks.getKnownPlateBlocksIterable()) {
                    pOutput.accept(block);
                }

                for (TrayItem item : ModItems.getTrayItemsArray()) {
                    pOutput.accept(item);
                }

            })
            .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
