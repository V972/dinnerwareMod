package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.world.level.block.Blocks;
import net.v972.dinnerware.util.ModTags;

public final class FabricBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public FabricBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(ModTags.Blocks.PLATE_SOFT_BREAKERS)
            .add(Blocks.END_ROD)
            .add(Blocks.SPONGE)
            .add(Blocks.WET_SPONGE);
    }
}