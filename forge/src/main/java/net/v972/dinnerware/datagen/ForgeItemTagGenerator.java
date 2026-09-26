package net.v972.dinnerware.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.registry.ForgeModItems;

public final class ForgeItemTagGenerator extends ItemTagsProvider {
    public ForgeItemTagGenerator(DataGenerator generator, ForgeBlockTagGenerator blockTags, ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, DinnerwareConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Forge Item Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags() {
        this.tag(ItemTags.PIGLIN_LOVED)
            .replace(false)
            .add(ForgeModItems.PLATE_ITEM_GOLD.get())
            .add(ForgeModItems.TRAY_GOLD.get());
    }
}