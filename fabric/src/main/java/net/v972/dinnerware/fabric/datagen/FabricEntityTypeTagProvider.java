package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.world.entity.EntityType;
import net.v972.dinnerware.util.ModTags;

public final class FabricEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    public FabricEntityTypeTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(ModTags.Entities.FRAGILE_PLATE_IGNORED)
            .add(EntityType.ITEM)
            .add(EntityType.FISHING_BOBBER)
            .add(EntityType.CAT);
    }
}