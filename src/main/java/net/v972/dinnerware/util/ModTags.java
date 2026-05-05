package net.v972.dinnerware.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
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
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ADDITIONAL_FOOD = tag("additional_food");

        public static final TagKey<Item> PLATES = tag("plates");
        public static final TagKey<Item> PLATES_REGULAR = tag("plates/regular");
        // public static final TagKey<Item> PLATES_BANQUET = tag("plates/banquet"); // WIP
        public static final TagKey<Item> PLATES_ONE_TRAY_LIST = tag("plates/one_tray_list"); // should be under 64 items

        public static final TagKey<Item> TRAYS = tag("trays");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> FRAGILE_PLATE_IGNORED = tag("fragile_plate_ignored");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, name)
            );
        }
    }
}
