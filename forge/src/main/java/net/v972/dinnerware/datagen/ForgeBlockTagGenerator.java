package net.v972.dinnerware.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.registry.ForgeModBlocks;

import java.util.concurrent.CompletableFuture;

public final class ForgeBlockTagGenerator extends BlockTagsProvider {
    public ForgeBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DinnerwareConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Forge Block Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.GUARDED_BY_PIGLINS)
            .replace(false)
            .add(ForgeModBlocks.PLATE_BLOCK_GOLD.get());
    }
}