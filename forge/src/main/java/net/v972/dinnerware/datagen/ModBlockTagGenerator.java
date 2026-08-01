package net.v972.dinnerware.datagen;

import net.minecraft.world.level.block.Blocks;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DinnerwareCommon.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Common Block Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.PLATE_SOFT_BREAKERS)
            .add(Blocks.END_ROD)
            .add(Blocks.SPONGE)
            .add(Blocks.WET_SPONGE);
    }
}
