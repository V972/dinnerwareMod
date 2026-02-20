package net.v972.dinnerware.datagen;

import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DinnerwareMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModBlocks.PLATE_BLOCK.get()).forAllStates(
                blockState -> new ConfiguredModel[]{
                        new ConfiguredModel(
                                models().getExistingFile(modLoc("plate_block"))
                        )}
        );
        simpleBlockItem(ModBlocks.PLATE_BLOCK.get(),
                models().getExistingFile(modLoc("plate_block")));
    }

//    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
//        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
//    }
}
