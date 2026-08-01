package net.v972.dinnerware.datagen;

import net.v972.dinnerware.DinnerwareCommon;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = DinnerwareCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        PackOutput forgeOutput = generator.getPackOutput();
        PackOutput commonOutput = new PackOutput(DinnerwareDatagenPaths.commonOutput());

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(forgeOutput));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(commonOutput));

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(commonOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(commonOutput, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModAdvancementProvider(commonOutput, lookupProvider,existingFileHelper));

        ModBlockTagGenerator sharedBlockTags = generator.addProvider(event.includeServer(), new ModBlockTagGenerator(commonOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(commonOutput, lookupProvider, sharedBlockTags.contentsGetter(), existingFileHelper));

        ForgeBlockTagGenerator forgeBlockTags = generator.addProvider(event.includeServer(),new ForgeBlockTagGenerator(forgeOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ForgeItemTagGenerator(forgeOutput, lookupProvider, forgeBlockTags.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new ModEntityTypeTagsGenerator(commonOutput, lookupProvider, existingFileHelper));
    }
}
