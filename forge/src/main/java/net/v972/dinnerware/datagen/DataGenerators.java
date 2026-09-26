package net.v972.dinnerware.datagen;

import net.minecraft.SharedConstants;
import net.v972.dinnerware.DinnerwareCommon;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator forgeGenerator  = event.getGenerator();
        DataGenerator commonGenerator = new DataGenerator(
            DinnerwareDatagenPaths.commonOutput(),
            forgeGenerator.getInputFolders(),
            SharedConstants.getCurrentVersion(),
            true
        );

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        forgeGenerator.addProvider(event.includeServer(), new ModRecipeProvider(forgeGenerator));
        forgeGenerator.addProvider(event.includeServer(), new ModLootTableProvider(commonGenerator));

        forgeGenerator.addProvider(event.includeClient(), new ModBlockStateProvider(commonGenerator, existingFileHelper));
        forgeGenerator.addProvider(event.includeClient(), new ModItemModelProvider(commonGenerator, existingFileHelper));

        forgeGenerator.addProvider(event.includeServer(), new ModAdvancementProvider(commonGenerator, existingFileHelper));

        ModBlockTagGenerator sharedBlockTags = new ModBlockTagGenerator(commonGenerator, existingFileHelper);
        forgeGenerator.addProvider(event.includeServer(), sharedBlockTags);

        forgeGenerator.addProvider(event.includeServer(), new ModItemTagGenerator(commonGenerator, sharedBlockTags, existingFileHelper));

        ForgeBlockTagGenerator forgeBlockTags = new ForgeBlockTagGenerator(forgeGenerator, existingFileHelper);
        forgeGenerator.addProvider(event.includeServer(), forgeBlockTags);

        forgeGenerator.addProvider(event.includeServer(), new ForgeItemTagGenerator(forgeGenerator, forgeBlockTags, existingFileHelper));

        forgeGenerator.addProvider(event.includeServer(), new ModEntityTypeTagsGenerator(commonGenerator, existingFileHelper));
    }
}
