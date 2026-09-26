package net.v972.dinnerware.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.registry.ForgeModBlocks;

public final class ForgeBlockTagGenerator extends BlockTagsProvider {
    public ForgeBlockTagGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DinnerwareConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Forge Block Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags() {
        tag(BlockTags.GUARDED_BY_PIGLINS)
            .replace(false)
            .add(ForgeModBlocks.PLATE_BLOCK_GOLD.get());
    }
}