package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.concurrent.CompletableFuture;

public final class FabricVanillaItemTagProvider
        extends FabricTagProvider.ItemTagProvider {

    public FabricVanillaItemTagProvider(
            FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, lookupProvider, blockTagProvider);
    }

    @Override
    public String getName() {
        return "Fabric Vanilla Item Tags: dinnerware";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED)
            .setReplace(false)
            .add(
                FabricModItems.plateItem(PlateDefinition.GOLD),
                FabricModItems.goldTray()
            );
    }
}