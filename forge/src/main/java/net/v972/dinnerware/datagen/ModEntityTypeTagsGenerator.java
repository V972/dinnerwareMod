package net.v972.dinnerware.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.util.ModTags;

public class ModEntityTypeTagsGenerator extends EntityTypeTagsProvider {
    public ModEntityTypeTagsGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DinnerwareConstants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Common Entity Type Tags: " + DinnerwareConstants.MOD_ID;
    }

    @Override
    protected void addTags() {
        tag(ModTags.Entities.FRAGILE_PLATE_IGNORED)
            .add(EntityType.ITEM)
            .add(EntityType.FISHING_BOBBER)
            .add(EntityType.CAT);
    }
}
