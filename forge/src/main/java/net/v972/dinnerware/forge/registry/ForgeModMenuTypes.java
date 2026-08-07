package net.v972.dinnerware.forge.registry;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.registry.DinnerwareRegistryNames;
import net.v972.dinnerware.screen.PlateMenu;

public class ForgeModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DinnerwareCommon.MOD_ID);

    public static final RegistryObject<MenuType<PlateMenu>> PLATE_MENU =
            registerMenuType(DinnerwareRegistryNames.Menus.PLATE_MENU, PlateMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    public static MenuType<PlateMenu> plateMenu() {
        return PLATE_MENU.get();
    }
}
