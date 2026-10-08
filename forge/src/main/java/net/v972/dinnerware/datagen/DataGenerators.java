package net.v972.dinnerware.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.v972.dinnerware.DinnerwareCommon;

@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator forgeGenerator = event.getGenerator();
        DataGenerator commonGenerator = new DataGenerator(
            DinnerwareDatagenPaths.commonOutput(),
            forgeGenerator.getInputFolders()
        );

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            // Recipes are loader-specific because recipe advancements need the loader-specific recipe folder
            forgeGenerator.addProvider(new ModRecipeProvider(forgeGenerator));

            // Common server data
            forgeGenerator.addProvider(new ModLootTableProvider(commonGenerator));
            forgeGenerator.addProvider(new ModAdvancementProvider(commonGenerator, existingFileHelper));

            ModBlockTagGenerator sharedBlockTags = new ModBlockTagGenerator(commonGenerator, existingFileHelper);
            forgeGenerator.addProvider(sharedBlockTags);
            forgeGenerator.addProvider(new ModItemTagGenerator(commonGenerator, sharedBlockTags, existingFileHelper));

            // Forge-specific tags
            ForgeBlockTagGenerator forgeBlockTags = new ForgeBlockTagGenerator(forgeGenerator, existingFileHelper);
            forgeGenerator.addProvider(forgeBlockTags);
            forgeGenerator.addProvider(new ForgeItemTagGenerator(forgeGenerator, forgeBlockTags, existingFileHelper));

            forgeGenerator.addProvider(new ModEntityTypeTagsGenerator(commonGenerator, existingFileHelper));
        }

        if (event.includeClient()) {
            forgeGenerator.addProvider(new ModBlockStateProvider(commonGenerator, existingFileHelper));
            forgeGenerator.addProvider(new ModItemModelProvider(commonGenerator, existingFileHelper));
        }
    }
}