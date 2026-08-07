package net.v972.dinnerware.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.registry.ForgeModItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class ForgeItemTagGenerator extends ItemTagsProvider {
    public ForgeItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, DinnerwareConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Forge Item Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(ItemTags.PIGLIN_LOVED)
            .replace(false)
            .add(ForgeModItems.PLATE_ITEM_GOLD.get())
            .add(ForgeModItems.TRAY_GOLD.get());
    }
}