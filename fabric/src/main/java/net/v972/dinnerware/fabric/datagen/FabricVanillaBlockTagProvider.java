package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.BlockTags;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;
import net.v972.dinnerware.registry.PlateDefinition;

public final class FabricVanillaBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public FabricVanillaBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS)
            .setReplace(false)
            .add(FabricModBlocks.plateBlock(PlateDefinition.GOLD));
    }
}