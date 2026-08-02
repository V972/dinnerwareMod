package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.v972.dinnerware.datagen.DinnerwareDatagenPaths;
import net.v972.dinnerware.util.ModTags;

import java.util.concurrent.CompletableFuture;

public final class FabricEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    public FabricEntityTypeTagProvider(
            FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider>
            lookupProvider) {
        super(
            new FabricDataOutput(
                output.getModContainer(),
                DinnerwareDatagenPaths.commonOutput(),
                output.isStrictValidationEnabled()
            ),
            lookupProvider
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModTags.Entities.FRAGILE_PLATE_IGNORED)
            .add(EntityType.ITEM)
            .add(EntityType.FISHING_BOBBER)
            .add(EntityType.CAT);
    }
}