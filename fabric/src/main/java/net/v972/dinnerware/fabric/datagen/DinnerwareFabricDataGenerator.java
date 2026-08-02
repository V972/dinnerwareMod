package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class DinnerwareFabricDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(FabricEntityTypeTagProvider::new);
        FabricBlockTagProvider commonBlockTags = pack.addProvider(FabricBlockTagProvider::new);
        pack.addProvider((output, lookupProvider) ->
            new FabricItemTagProvider(
                output,
                lookupProvider,
                commonBlockTags
            )
        );
        FabricVanillaBlockTagProvider fabricBlockTags = pack.addProvider(FabricVanillaBlockTagProvider::new);
        pack.addProvider((output, lookupProvider) ->
            new FabricVanillaItemTagProvider(
                output,
                lookupProvider,
                fabricBlockTags
            )
        );
        pack.addProvider(DinnerwareFabricAdvancementProvider::new);
        pack.addProvider(DinnerwareFabricBlockLootProvider::new);
        pack.addProvider(DinnerwareFabricRecipeProvider::new);
        pack.addProvider(DinnerwareFabricModelProvider::new);
    }
}