package net.v972.dinnerware.datagen;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.util.DinnerwareHelper;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DinnerwareMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(Block block : ModBlocks.getKnownBlocks()) {
            PlateBlock plateBlock = (PlateBlock)block;

            ModelFile model = DinnerwareHelper.getPlateModelWithMaterial(plateBlock, models());

            this.getVariantBuilder(plateBlock)
                .forAllStatesExcept(state ->
                    ConfiguredModel
                        .builder()
                        .modelFile(model)
                        .rotationY((int)state.getValue(HORIZONTAL_FACING).toYRot())
                        .build(),
                    WATERLOGGED);
            simpleBlockItem(plateBlock, model);
        }
    }
}
