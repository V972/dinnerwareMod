package net.v972.dinnerware.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.registry.ForgeModItems;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, DinnerwareConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Common Item Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(ModTags.Items.PLATES_REGULAR)
            .add(ForgeModItems.getKnownPlateItemsArray());
        this.tag(ModTags.Items.PLATES)
            .add(ForgeModItems.getKnownPlateItemsArray());

        this.tag(ModTags.Items.PLATES_ONE_TRAY_LIST)
            .add(ForgeModItems.getSurvivalPlateItemsArray());

        this.tag(ModTags.Items.TRAYS)
            .add(ForgeModItems.getTrayItemsArray());
    }
}
