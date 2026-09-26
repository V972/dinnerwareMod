package net.v972.dinnerware.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, DinnerwareCommon.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Common Block Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags() {
        this.tag(ModTags.Blocks.PLATE_SOFT_BREAKERS)
            .add(Blocks.END_ROD)
            .add(Blocks.SPONGE)
            .add(Blocks.WET_SPONGE);
    }
}