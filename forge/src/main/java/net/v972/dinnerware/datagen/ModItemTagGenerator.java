package net.v972.dinnerware.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.forge.registry.ForgeModItems;
import net.v972.dinnerware.util.ModTags;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(DataGenerator generator, ModBlockTagGenerator blockTags, ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, DinnerwareConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Common Item Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags() {
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