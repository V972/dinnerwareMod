package net.v972.dinnerware.forge;

import net.neoforged.fml.ModContainer;
import net.neoforged.bus.api.IEventBus;
import net.v972.dinnerware.block.DinnerwareBlocks;
import net.v972.dinnerware.block.entity.DinnerwareBlockEntities;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.config.DinnerwareConfig;
import net.v972.dinnerware.forge.config.DinnerwareForgeConfigs;
import net.v972.dinnerware.forge.config.ForgeDinnerwareConfigBackend;
import net.v972.dinnerware.forge.registry.*;
import net.v972.dinnerware.item.DinnerwareItems;
import net.v972.dinnerware.platform.PlatformHooks;
import net.v972.dinnerware.screen.DinnerwareMenuTypes;
import net.v972.dinnerware.screen.PlateMenu;

public final class DinnerwareForgeBootstrap {
    private DinnerwareForgeBootstrap() {
    }

    public static void registerRegistries(IEventBus modEventBus) {
        DinnerwareForgeRegistries.register(modEventBus);
    }

    public static void registerConfigs(ModContainer modContainer) {
        DinnerwareForgeConfigs.register(modContainer);
    }

    public static void registerModEventHandlers(IEventBus modEventBus) {
        modEventBus.addListener(DinnerwareForgeLifecycleEvents::commonSetup);
        modEventBus.addListener(DinnerwareForgeLifecycleEvents::loadComplete);
        modEventBus.addListener(DinnerwareForgeLifecycleEvents::registerCapabilities);
        modEventBus.addListener(DinnerwareForgeLifecycleEvents::registerPayloads);
    }

    // ========================================

    public static void registerPlatformHooks() {
        PlatformHooks.setPlatform(new DinnerwareForgePlatform());
        DinnerwareConfig.setBackend(ForgeDinnerwareConfigBackend.INSTANCE);
        DinnerwareMenuTypes.setPlateMenuSupplier(ForgeModMenuTypes::plateMenu);
        PlateBlockBlockEntity.setMenuFactory(PlateMenu::new);

        DinnerwareBlockEntities.setPlateBlockEntityTypeSupplier(ForgeModBlockEntities::plateBlockEntityType);
        DinnerwareBlocks.setKnownPlateBlocksIterableSupplier(ForgeModBlocks::getKnownPlateBlocksIterable);
        DinnerwareBlocks.setKnownPlateBlocksArraySupplier(ForgeModBlocks::getKnownPlateBlocksArray);

        DinnerwareItems.setTrayItemsArraySupplier(ForgeModItems::getTrayItemsArray);
        DinnerwareItems.setSurvivalPlateItemsArraySupplier(ForgeModItems::getSurvivalPlateItemsArray);
        DinnerwareItems.setKnownPlateItemsSetSupplier(ForgeModItems::getKnownPlateItemsSet);
        DinnerwareItems.setTrayItemsSetSupplier(ForgeModItems::getTrayItemsSet);
    }
}