package net.v972.dinnerware.fabric;

import net.fabricmc.api.ModInitializer;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.advancement.ModCriterionTriggers;
import net.v972.dinnerware.block.DinnerwareBlocks;
import net.v972.dinnerware.block.entity.DinnerwareBlockEntities;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.config.DinnerwareConfig;
import net.v972.dinnerware.fabric.config.DinnerwareFabricConfigs;
import net.v972.dinnerware.fabric.config.FabricDinnerwareConfigBackend;
import net.v972.dinnerware.fabric.network.FabricNetwork;
import net.v972.dinnerware.fabric.registry.*;
import net.v972.dinnerware.item.DinnerwareItems;
import net.v972.dinnerware.platform.PlatformHooks;
import net.v972.dinnerware.screen.DinnerwareMenuTypes;
import net.v972.dinnerware.screen.PlateMenu;

public final class DinnerwareFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        registerCommonHooks();

        FabricNetwork.register();

        DinnerwareFabricConfigs.register();
        DinnerwareConfig.setBackend(FabricDinnerwareConfigBackend.INSTANCE);

        DinnerwareCommon.init();

        FabricModBlocks.register();
        FabricModItems.register();
        FabricModBlockEntities.register();
        FabricModStorage.register();
        FabricModMenuTypes.register();
        FabricModCreativeModeTabs.register();
        FabricModCriterionTriggers.register();

        DinnerwareCommon.registerDispenserBehaviors();
    }

    private static void registerCommonHooks() {
        PlatformHooks.setPlatform(new DinnerwareFabricPlatform());

        DinnerwareBlocks.setKnownPlateBlocksIterableSupplier(
            FabricModBlocks::getKnownPlateBlocksIterable
        );
        DinnerwareBlocks.setKnownPlateBlocksArraySupplier(
            FabricModBlocks::getKnownPlateBlocksArray
        );

        DinnerwareItems.setTrayItemsArraySupplier(
            FabricModItems::getTrayItemsArray
        );
        DinnerwareItems.setSurvivalPlateItemsArraySupplier(
            FabricModItems::getSurvivalPlateItemsArray
        );
        DinnerwareItems.setKnownPlateItemsSetSupplier(
            FabricModItems::getKnownPlateItemsSet
        );
        DinnerwareItems.setTrayItemsSetSupplier(
            FabricModItems::getTrayItemsSet
        );

        DinnerwareBlockEntities.setPlateBlockEntityTypeSupplier(
            FabricModBlockEntities::plateBlockEntityType
        );

        DinnerwareMenuTypes.setPlateMenuSupplier(
            FabricModMenuTypes::plateMenu
        );

        PlateBlockBlockEntity.setMenuFactory(PlateMenu::new);

        ModCriterionTriggers.setManualTriggerSupplier(
            FabricModCriterionTriggers::manualTrigger
        );
    }
}