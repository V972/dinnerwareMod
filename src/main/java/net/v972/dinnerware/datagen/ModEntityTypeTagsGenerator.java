package net.v972.dinnerware.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsGenerator extends EntityTypeTagsProvider {
    public ModEntityTypeTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                      ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DinnerwareMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ModTags.Entities.FRAGILE_PLATE_IGNORED)
                .add(EntityType.ITEM)
                .add(EntityType.FISHING_BOBBER);
    }
}
