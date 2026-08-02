package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.concurrent.CompletableFuture;

public final class FabricVanillaBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public FabricVanillaBlockTagProvider(
            FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public String getName() {
        return "Fabric Vanilla Block Tags: dinnerware";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS)
            .setReplace(false)
            .add(FabricModBlocks.plateBlock(PlateDefinition.GOLD));
    }
}