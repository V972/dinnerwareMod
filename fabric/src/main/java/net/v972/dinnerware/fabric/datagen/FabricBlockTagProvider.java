package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import net.v972.dinnerware.datagen.DinnerwareDatagenPaths;
import net.v972.dinnerware.util.ModTags;

import java.util.concurrent.CompletableFuture;

public final class FabricBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public FabricBlockTagProvider(
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
    public String getName() {
        return "Common Block Tags: dinnerware";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModTags.Blocks.PLATE_SOFT_BREAKERS)
            .add(Blocks.END_ROD)
            .add(Blocks.SPONGE)
            .add(Blocks.WET_SPONGE);
    }
}