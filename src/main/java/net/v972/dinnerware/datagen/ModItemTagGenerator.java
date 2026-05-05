package net.v972.dinnerware.datagen;

import net.minecraft.tags.ItemTags;
import net.v972.dinnerware.DinnerwareMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, DinnerwareMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.PIGLIN_LOVED)
            .add(ModItems.PLATE_ITEM_GOLD.get())
            .add(ModItems.TRAY_GOLD.get());

        this.tag(ModTags.Items.PLATES_REGULAR)
                .add(ModItems.getPlateItemsArray());
        this.tag(ModTags.Items.PLATES)
                .add(ModItems.getPlateItemsArray());

        this.tag(ModTags.Items.PLATES_ONE_TRAY_LIST)
                .add(ModItems.getSurvivalPlateItemsArray());

        this.tag(ModTags.Items.TRAYS)
                .add(ModItems.getTrayItemsArray());
    }
}
