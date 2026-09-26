package net.v972.dinnerware.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.v972.dinnerware.datagen.DinnerwareDatagenPaths;

public final class DinnerwareFabricDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator commonGenerator = new FabricDataGenerator(
            DinnerwareDatagenPaths.commonOutput(),
            dataGenerator.getModContainer(),
            dataGenerator.isStrictValidationEnabled()
        );

        // Common output.
        dataGenerator.addProvider(
            new DinnerwareFabricAdvancementProvider(commonGenerator)
        );
        dataGenerator.addProvider(
            new DinnerwareFabricBlockLootProvider(commonGenerator)
        );
        dataGenerator.addProvider(
            new DinnerwareFabricModelProvider(commonGenerator)
        );

        FabricBlockTagProvider commonBlockTags =
            new FabricBlockTagProvider(commonGenerator);
        dataGenerator.addProvider(
            new NamedDataProvider(
                "Common Block Tags: dinnerware", commonBlockTags)
        );
        FabricItemTagProvider commonItemTags =
            new FabricItemTagProvider(commonGenerator, commonBlockTags);
        dataGenerator.addProvider(
            new NamedDataProvider(
                "Common Item Tags: dinnerware", commonItemTags )
        );
        FabricEntityTypeTagProvider commonEntityTypeTags =
            new FabricEntityTypeTagProvider(commonGenerator);
        dataGenerator.addProvider(
            new NamedDataProvider(
                "Common Entity Type Tags: dinnerware", commonEntityTypeTags)
        );


        // Fabric-specific output.
        dataGenerator.addProvider(
            new DinnerwareFabricRecipeProvider(dataGenerator)
        );

        FabricVanillaBlockTagProvider fabricBlockTags =
            new FabricVanillaBlockTagProvider(dataGenerator);
        dataGenerator.addProvider(
            new NamedDataProvider(
                "Fabric Block Tags: dinnerware", fabricBlockTags)
        );
        FabricVanillaItemTagProvider fabricItemTags =
            new FabricVanillaItemTagProvider(dataGenerator, fabricBlockTags);
        dataGenerator.addProvider(
            new NamedDataProvider(
                "Fabric Item Tags: dinnerware", fabricItemTags)
        );
    }
}