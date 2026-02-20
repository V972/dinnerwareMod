package net.v972.dinnerware.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DinnerwareMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModBlocks.PLATE_BLOCK.get()).forAllStates(
                blockState -> new ConfiguredModel[]{
                        new ConfiguredModel(
                                models().getExistingFile(modLoc("basic_plate"))
                        )}
        );
        simpleBlockItem(ModBlocks.PLATE_BLOCK.get(),
                models().getExistingFile(modLoc("basic_plate")));

        getVariantBuilder(ModBlocks.PLATE_BLOCK.get()).forAllStates(
                blockState -> new ConfiguredModel[]{
                        new ConfiguredModel(
                        models().getExistingFile(modLoc("basic_plate"))
                )}
        );
        simpleBlockItem(ModBlocks.PLATE_BLOCK.get(),
                models().getExistingFile(modLoc("basic_plate")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
