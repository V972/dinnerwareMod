package net.v972.dinnerware.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.function.Supplier;
import java.util.stream.Stream;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, DinnerwareCommon.MOD_ID);

    // ========================================

    public static final RegistryObject<Block> PLATE_BLOCK_BEDROCK = BLOCKS.register(PlateDefinition.BEDROCK.block(),
        () -> new PlateBlock(PlateDefinition.BEDROCK.material(),
            BlockBehaviour.Properties.copy(PlateDefinition.BEDROCK.material())
                .isValidSpawn((state, getter, pos, entityType) -> false)
                .isRedstoneConductor((state, level, pos) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .pushReaction(PushReaction.BLOCK)
        ));

    public static final RegistryObject<Block> PLATE_BLOCK_QUARTZ = BLOCKS.register(PlateDefinition.QUARTZ.block(),
        () -> new PlateBlock(PlateDefinition.QUARTZ.material(), Ingredient.of(Tags.Items.GEMS_QUARTZ), 1, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHISELED_QUARTZ = BLOCKS.register(PlateDefinition.CHISELED_QUARTZ.block(),
        () -> new PlateBlock(PlateDefinition.CHISELED_QUARTZ.material(), getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_IRON = BLOCKS.register(PlateDefinition.IRON.block(),
        () -> new PlateBlock(PlateDefinition.IRON.material(), Ingredient.of(Tags.Items.INGOTS_IRON), 1, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GOLD = BLOCKS.register(PlateDefinition.GOLD.block(),
        () -> new PlateBlock(PlateDefinition.GOLD.material(), Ingredient.of(Tags.Items.INGOTS_GOLD), 1, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_DIAMOND = BLOCKS.register(PlateDefinition.DIAMOND.block(),
        () -> new PlateBlock(PlateDefinition.DIAMOND.material(), Ingredient.of(Tags.Items.GEMS_DIAMOND), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_OBSIDIAN = BLOCKS.register(PlateDefinition.OBSIDIAN.block(),
        () -> new PlateBlock(PlateDefinition.OBSIDIAN.material(), getDefaultPlateProperties().pushReaction(PushReaction.BLOCK)));

    public static final RegistryObject<Block> PLATE_BLOCK_POLISHED_BLACKSTONE = BLOCKS.register(PlateDefinition.POLISHED_BLACKSTONE.block(),
        () -> new PlateBlock(PlateDefinition.POLISHED_BLACKSTONE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHISELED_NETHER_BRICKS = BLOCKS.register(PlateDefinition.CHISELED_NETHER_BRICKS.block(),
        () -> new PlateBlock(PlateDefinition.CHISELED_NETHER_BRICKS.material(), Ingredient.of(Tags.Items.INGOTS_NETHER_BRICK), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_PURPUR = BLOCKS.register(PlateDefinition.PURPUR.block(),
        () -> new PlateBlock(PlateDefinition.PURPUR.material(), Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_OAK = BLOCKS.register(PlateDefinition.OAK.block(),
        () -> new PlateBlock(PlateDefinition.OAK.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BIRCH = BLOCKS.register(PlateDefinition.BIRCH.block(),
        () -> new PlateBlock(PlateDefinition.BIRCH.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_SPRUCE = BLOCKS.register(PlateDefinition.SPRUCE.block(),
        () -> new PlateBlock(PlateDefinition.SPRUCE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_JUNGLE = BLOCKS.register(PlateDefinition.JUNGLE.block(),
        () -> new PlateBlock(PlateDefinition.JUNGLE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ACACIA = BLOCKS.register(PlateDefinition.ACACIA.block(),
        () -> new PlateBlock(PlateDefinition.ACACIA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_DARK_OAK = BLOCKS.register(PlateDefinition.DARK_OAK.block(),
        () -> new PlateBlock(PlateDefinition.DARK_OAK.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHERRY = BLOCKS.register(PlateDefinition.CHERRY.block(),
        () -> new PlateBlock(PlateDefinition.CHERRY.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MANGROVE = BLOCKS.register(PlateDefinition.MANGROVE.block(),
        () -> new PlateBlock(PlateDefinition.MANGROVE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BAMBOO = BLOCKS.register(PlateDefinition.BAMBOO.block(),
        () -> new PlateBlock(PlateDefinition.BAMBOO.material(), getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_CRIMSON = BLOCKS.register(PlateDefinition.CRIMSON.block(),
        () -> new PlateBlock(PlateDefinition.CRIMSON.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_WARPED = BLOCKS.register(PlateDefinition.WARPED.block(),
        () -> new PlateBlock(PlateDefinition.WARPED.material(), getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_TERRACOTTA = BLOCKS.register(PlateDefinition.TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.TERRACOTTA.material(), getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_WHITE_TERRACOTTA = BLOCKS.register(PlateDefinition.WHITE_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.WHITE_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ORANGE_TERRACOTTA = BLOCKS.register(PlateDefinition.ORANGE_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.ORANGE_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MAGENTA_TERRACOTTA = BLOCKS.register(PlateDefinition.MAGENTA_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.MAGENTA_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA = BLOCKS.register(PlateDefinition.LIGHT_BLUE_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.LIGHT_BLUE_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_YELLOW_TERRACOTTA = BLOCKS.register(PlateDefinition.YELLOW_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.YELLOW_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIME_TERRACOTTA = BLOCKS.register(PlateDefinition.LIME_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.LIME_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PINK_TERRACOTTA = BLOCKS.register(PlateDefinition.PINK_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.PINK_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GRAY_TERRACOTTA = BLOCKS.register(PlateDefinition.GRAY_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.GRAY_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA = BLOCKS.register(PlateDefinition.LIGHT_GRAY_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.LIGHT_GRAY_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CYAN_TERRACOTTA = BLOCKS.register(PlateDefinition.CYAN_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.CYAN_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PURPLE_TERRACOTTA = BLOCKS.register(PlateDefinition.PURPLE_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.PURPLE_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLUE_TERRACOTTA = BLOCKS.register(PlateDefinition.BLUE_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.BLUE_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BROWN_TERRACOTTA = BLOCKS.register(PlateDefinition.BROWN_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.BROWN_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GREEN_TERRACOTTA = BLOCKS.register(PlateDefinition.GREEN_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.GREEN_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_RED_TERRACOTTA = BLOCKS.register(PlateDefinition.RED_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.RED_TERRACOTTA.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLACK_TERRACOTTA = BLOCKS.register(PlateDefinition.BLACK_TERRACOTTA.block(),
        () -> new PlateBlock(PlateDefinition.BLACK_TERRACOTTA.material(), getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_WHITE_CONCRETE = BLOCKS.register(PlateDefinition.WHITE_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.WHITE_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ORANGE_CONCRETE = BLOCKS.register(PlateDefinition.ORANGE_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.ORANGE_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MAGENTA_CONCRETE = BLOCKS.register(PlateDefinition.MAGENTA_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.MAGENTA_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_BLUE_CONCRETE = BLOCKS.register(PlateDefinition.LIGHT_BLUE_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.LIGHT_BLUE_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_YELLOW_CONCRETE = BLOCKS.register(PlateDefinition.YELLOW_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.YELLOW_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIME_CONCRETE = BLOCKS.register(PlateDefinition.LIME_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.LIME_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PINK_CONCRETE = BLOCKS.register(PlateDefinition.PINK_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.PINK_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GRAY_CONCRETE = BLOCKS.register(PlateDefinition.GRAY_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.GRAY_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_GRAY_CONCRETE = BLOCKS.register(PlateDefinition.LIGHT_GRAY_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.LIGHT_GRAY_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CYAN_CONCRETE = BLOCKS.register(PlateDefinition.CYAN_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.CYAN_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PURPLE_CONCRETE = BLOCKS.register(PlateDefinition.PURPLE_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.PURPLE_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLUE_CONCRETE = BLOCKS.register(PlateDefinition.BLUE_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.BLUE_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BROWN_CONCRETE = BLOCKS.register(PlateDefinition.BROWN_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.BROWN_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GREEN_CONCRETE = BLOCKS.register(PlateDefinition.GREEN_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.GREEN_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_RED_CONCRETE = BLOCKS.register(PlateDefinition.RED_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.RED_CONCRETE.material(), getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLACK_CONCRETE = BLOCKS.register(PlateDefinition.BLACK_CONCRETE.block(),
        () -> new PlateBlock(PlateDefinition.BLACK_CONCRETE.material(), getDefaultPlateProperties()));

    // ========================================

    public static Block[] getKnownBlocksArray() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new);
    }

    public static Iterable<Block> getKnownBlocks() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    public static Iterable<Pair<Block, Ingredient>> getTerracottaDyeingMap() {
        return Stream.of(
            new Pair<>(PLATE_BLOCK_WHITE_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_WHITE)),
            new Pair<>(PLATE_BLOCK_ORANGE_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_ORANGE)),
            new Pair<>(PLATE_BLOCK_MAGENTA_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_MAGENTA)),
            new Pair<>(PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_LIGHT_BLUE)),
            new Pair<>(PLATE_BLOCK_YELLOW_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_YELLOW)),
            new Pair<>(PLATE_BLOCK_LIME_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_LIME)),
            new Pair<>(PLATE_BLOCK_PINK_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_PINK)),
            new Pair<>(PLATE_BLOCK_GRAY_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_GRAY)),
            new Pair<>(PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_LIGHT_GRAY)),
            new Pair<>(PLATE_BLOCK_CYAN_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_CYAN)),
            new Pair<>(PLATE_BLOCK_PURPLE_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_PURPLE)),
            new Pair<>(PLATE_BLOCK_BLUE_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_BLUE)),
            new Pair<>(PLATE_BLOCK_BROWN_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_BROWN)),
            new Pair<>(PLATE_BLOCK_GREEN_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_GREEN)),
            new Pair<>(PLATE_BLOCK_RED_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_RED)),
            new Pair<>(PLATE_BLOCK_BLACK_TERRACOTTA.get(), Ingredient.of(Tags.Items.DYES_BLACK))
        )::iterator;
    }

    // ========================================

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    private static BlockBehaviour.Properties getDefaultPlateProperties() {
        return BlockBehaviour.Properties.of()
                .strength(0.3F)
                .isValidSpawn((state, getter, pos, entityType) -> false)
                .isRedstoneConductor((state, level, pos) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .pushReaction(PushReaction.DESTROY)
                .instabreak()
        ;
    }

    // ========================================

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
