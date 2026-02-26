package net.v972.dinnerware.util;

import net.v972.dinnerware.DinnerwareMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(DinnerwareMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ADDITIONAL_FOOD = tag("additional_food");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(DinnerwareMod.MOD_ID, name));
        }
    }
}
