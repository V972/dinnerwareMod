package net.v972.dinnerware.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.item.custom.TrayItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DinnerwareMod.MOD_ID);

    public  static final RegistryObject<Item> ICON = ITEMS.register("dinnerware_icon",
            () -> new Item(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> TRAY = ITEMS.register("tray",
            () -> new TrayItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
