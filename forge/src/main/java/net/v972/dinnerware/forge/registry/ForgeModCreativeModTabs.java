package net.v972.dinnerware.forge.registry;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

public class ForgeModCreativeModTabs {
    public static final CreativeModeTab DINNERWARE_TAB =
        new CreativeModeTab(CreativeModeTab.TABS.length, "dinnerware_tab") {

            @Override
            public @NotNull ItemStack makeIcon() {
                return new ItemStack(ForgeModItems.icon());
            }

            @Override
            public void fillItemList(NonNullList<ItemStack> stacks) {
                for (Item plate : ForgeModItems.getKnownPlateItemsArray()) {
                    stacks.add(new ItemStack(plate));
                }

                for (Item tray : ForgeModItems.getTrayItemsArray()) {
                    stacks.add(new ItemStack(tray));
                }
            }
        };

    public static void register(IEventBus eventBus) { }
}