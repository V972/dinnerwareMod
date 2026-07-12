package net.v972.dinnerware.forge.registry;

import net.minecraft.world.level.block.Block;
import net.v972.dinnerware.DinnerwareCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;

public class ForgeModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DinnerwareCommon.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DINNERWARE_TAB = CREATIVE_MODE_TABS.register(
            DinnerwareRegistryNames.CreativeTabs.DINNERWARE_TAB,
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ForgeModItems.icon()))
            .title(Component.translatable("creativetab.dinnerware_tab"))
            .displayItems((pParameters, pOutput) -> {
                for(Block block : ForgeModBlocks.getKnownPlateBlocksIterable()) {
                    pOutput.accept(block);
                }

                for (TrayItem item : ForgeModItems.getTrayItemsArray()) {
                    pOutput.accept(item);
                }

            })
            .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
