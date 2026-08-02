package net.v972.dinnerware.fabric.registry;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

public final class FabricModBlocks {
    private static final Map<PlateDefinition, Block> PLATE_BLOCKS =
        new EnumMap<>(PlateDefinition.class);

    private static boolean registered;

    private FabricModBlocks() {
    }

    public static void register() {
        if (registered) return;

        for (PlateDefinition definition : PlateDefinition.values()) {
            Block block = createPlateBlock(definition);

            PLATE_BLOCKS.put(definition, block);

            net.minecraft.core.Registry.register(
                BuiltInRegistries.BLOCK,
                DinnerwareConstants.id(definition.block()),
                block
            );
        }

        registered = true;
    }

    // ========================================

    public static Block plateBlock(PlateDefinition definition) {
        Block block = PLATE_BLOCKS.get(definition);

        if (block == null) {
            throw new IllegalStateException("Fabric plate blocks have not been registered");
        }

        return block;
    }

    public static Block[] getKnownPlateBlocksArray() {
        return PLATE_BLOCKS.values().toArray(Block[]::new);
    }

    public static Iterable<Block> getKnownPlateBlocksIterable() {
        return PLATE_BLOCKS.values();
    }

    public static Iterable<Pair<Block, Ingredient>> getTerracottaPlateBlocksDyeingMap() {
        return Stream.of(
            new Pair<>(plateBlock(PlateDefinition.WHITE_TERRACOTTA), Ingredient.of(ConventionalItemTags.WHITE_DYES)),
            new Pair<>(plateBlock(PlateDefinition.ORANGE_TERRACOTTA), Ingredient.of(ConventionalItemTags.ORANGE_DYES)),
            new Pair<>(plateBlock(PlateDefinition.MAGENTA_TERRACOTTA), Ingredient.of(ConventionalItemTags.MAGENTA_DYES)),
            new Pair<>(plateBlock(PlateDefinition.LIGHT_BLUE_TERRACOTTA), Ingredient.of(ConventionalItemTags.LIGHT_BLUE_DYES)),
            new Pair<>(plateBlock(PlateDefinition.YELLOW_TERRACOTTA), Ingredient.of(ConventionalItemTags.YELLOW_DYES)),
            new Pair<>(plateBlock(PlateDefinition.LIME_TERRACOTTA), Ingredient.of(ConventionalItemTags.LIME_DYES)),
            new Pair<>(plateBlock(PlateDefinition.PINK_TERRACOTTA), Ingredient.of(ConventionalItemTags.PINK_DYES)),
            new Pair<>(plateBlock(PlateDefinition.GRAY_TERRACOTTA), Ingredient.of(ConventionalItemTags.GRAY_DYES)),
            new Pair<>(plateBlock(PlateDefinition.LIGHT_GRAY_TERRACOTTA), Ingredient.of(ConventionalItemTags.LIGHT_GRAY_DYES)),
            new Pair<>(plateBlock(PlateDefinition.CYAN_TERRACOTTA), Ingredient.of(ConventionalItemTags.CYAN_DYES)),
            new Pair<>(plateBlock(PlateDefinition.PURPLE_TERRACOTTA), Ingredient.of(ConventionalItemTags.PURPLE_DYES)),
            new Pair<>(plateBlock(PlateDefinition.BLUE_TERRACOTTA), Ingredient.of(ConventionalItemTags.BLUE_DYES)),
            new Pair<>(plateBlock(PlateDefinition.BROWN_TERRACOTTA), Ingredient.of(ConventionalItemTags.BROWN_DYES)),
            new Pair<>(plateBlock(PlateDefinition.GREEN_TERRACOTTA), Ingredient.of(ConventionalItemTags.GREEN_DYES)),
            new Pair<>(plateBlock(PlateDefinition.RED_TERRACOTTA), Ingredient.of(ConventionalItemTags.RED_DYES)),
            new Pair<>(plateBlock(PlateDefinition.BLACK_TERRACOTTA), Ingredient.of(ConventionalItemTags.BLACK_DYES))
        )::iterator;
    }

    // ========================================

    private static Block createPlateBlock(PlateDefinition definition) {
        return switch (definition) {
            case BEDROCK -> new PlateBlock(
                definition.material(),
                BlockBehaviour.Properties.copy(definition.material())
                    .isValidSpawn((state, getter, pos, entityType) -> false)
                    .isRedstoneConductor((state, level, pos) -> false)
                    .isSuffocating((state, level, pos) -> false)
                    .isViewBlocking((state, level, pos) -> false)
                    .pushReaction(PushReaction.BLOCK)
            );

            case OBSIDIAN -> new PlateBlock(
                definition.material(),
                defaultPlateProperties()
                    .pushReaction(PushReaction.BLOCK)
            );

            case QUARTZ -> new PlateBlock(
                definition.material(),
                Ingredient.of(ConventionalItemTags.QUARTZ),
                1,
                defaultPlateProperties()
            );

            case IRON -> new PlateBlock(
                definition.material(),
                Ingredient.of(ConventionalItemTags.IRON_INGOTS),
                1,
                defaultPlateProperties()
            );

            case GOLD -> new PlateBlock(
                definition.material(),
                Ingredient.of(ConventionalItemTags.GOLD_INGOTS),
                1,
                defaultPlateProperties()
            );

            case DIAMOND -> new PlateBlock(
                definition.material(),
                Ingredient.of(ConventionalItemTags.DIAMONDS),
                1,
                defaultPlateProperties()
            );

            case CHISELED_NETHER_BRICKS -> new PlateBlock(
                definition.material(),
                Ingredient.of(Items.NETHER_BRICK),
                1,
                defaultPlateProperties()
            );

            case PURPUR -> new PlateBlock(
                definition.material(),
                Ingredient.of(Items.POPPED_CHORUS_FRUIT),
                1,
                defaultPlateProperties()
            );

            default -> new PlateBlock(
                definition.material(),
                defaultPlateProperties()
            );
        };
    }

    private static BlockBehaviour.Properties defaultPlateProperties() {
        return BlockBehaviour.Properties.of()
            .strength(0.3F)
            .isValidSpawn((state, getter, pos, entityType) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
            .isSuffocating((state, level, pos) -> false)
            .isViewBlocking((state, level, pos) -> false)
            .pushReaction(PushReaction.DESTROY)
            .instabreak();
    }
}