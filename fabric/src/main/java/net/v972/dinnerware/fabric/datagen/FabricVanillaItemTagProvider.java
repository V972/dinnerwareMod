package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.ItemTags;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.registry.PlateDefinition;

public final class FabricVanillaItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public FabricVanillaItemTagProvider(FabricDataGenerator dataGenerator, FabricVanillaBlockTagProvider blockTagProvider) {
        super(dataGenerator, blockTagProvider);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED)
            .setReplace(false)
            .add(
                FabricModItems.plateItem(PlateDefinition.GOLD),
                FabricModItems.goldTray()
            );
    }
}