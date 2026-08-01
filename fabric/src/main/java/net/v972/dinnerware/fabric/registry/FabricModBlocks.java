package net.v972.dinnerware.fabric.registry;

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

public final class FabricModBlocks {
    private static final Map<PlateDefinition, Block> PLATE_BLOCKS =
            new EnumMap<>(PlateDefinition.class);

    private static boolean registered;

    private FabricModBlocks() {
    }

    public static void register() {
        if (registered) {
            return;
        }

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