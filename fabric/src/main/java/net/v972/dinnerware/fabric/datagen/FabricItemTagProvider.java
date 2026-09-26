package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.world.item.Item;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.util.ModTags;

public final class FabricItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public FabricItemTagProvider(
            FabricDataGenerator dataGenerator,
            FabricBlockTagProvider blockTagProvider) {
        super(dataGenerator, blockTagProvider);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(ModTags.Items.PLATES_REGULAR)
            .add(FabricModItems.getKnownPlateItemsSet().toArray(Item[]::new));

        getOrCreateTagBuilder(ModTags.Items.PLATES)
            .add(FabricModItems.getKnownPlateItemsSet().toArray(Item[]::new));

        getOrCreateTagBuilder(ModTags.Items.PLATES_ONE_TRAY_LIST)
            .add(FabricModItems.getSurvivalPlateItemsArray());

        getOrCreateTagBuilder(ModTags.Items.TRAYS)
            .add(FabricModItems.getTrayItemsArray());
    }
}