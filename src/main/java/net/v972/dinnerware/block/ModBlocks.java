package net.v972.dinnerware.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DinnerwareMod.MOD_ID);

    // ========================================

    public static final RegistryObject<Block> PLATE_BLOCK_BEDROCK = registerBlock("bedrock_plate_block",
            () -> new PlateBlock(Blocks.BEDROCK,
                    BlockBehaviour.Properties.copy(Blocks.BEDROCK)
                            .isValidSpawn((state, getter, pos, entityType) -> false)
                            .isRedstoneConductor((state, level, pos) -> false)
                            .isSuffocating((state, level, pos) -> false)
                            .isViewBlocking((state, level, pos) -> false)
                            .pushReaction(PushReaction.BLOCK)
            ));

    public static final RegistryObject<Block> PLATE_BLOCK_QUARTZ = registerBlock("quartz_plate_block",
            () -> new PlateBlock(Blocks.QUARTZ_BLOCK, Ingredient.of(Tags.Items.GEMS_QUARTZ), 1, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHISELED_QUARTZ = registerBlock("chiseled_quartz_plate_block",
            () -> new PlateBlock(Blocks.CHISELED_QUARTZ_BLOCK, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_IRON = registerBlock("iron_plate_block",
            () -> new PlateBlock(Blocks.IRON_BLOCK, Ingredient.of(Tags.Items.INGOTS_IRON), 1, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GOLD = registerBlock("gold_plate_block",
            () -> new PlateBlock(Blocks.GOLD_BLOCK, Ingredient.of(Tags.Items.INGOTS_GOLD), 1, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_DIAMOND = registerBlock("diamond_plate_block",
            () -> new PlateBlock(Blocks.DIAMOND_BLOCK, Ingredient.of(Tags.Items.GEMS_DIAMOND), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_OBSIDIAN = registerBlock("obsidian_plate_block",
            () -> new PlateBlock(Blocks.OBSIDIAN, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_POLISHED_BLACKSTONE = registerBlock("polished_blackstone_plate_block",
            () -> new PlateBlock(Blocks.POLISHED_BLACKSTONE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHISELED_NETHER_BRICKS = registerBlock("chiseled_nether_bricks_plate_block",
            () -> new PlateBlock(Blocks.CHISELED_NETHER_BRICKS, Ingredient.of(Tags.Items.INGOTS_NETHER_BRICK), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_PURPUR = registerBlock("purpur_plate_block",
            () -> new PlateBlock(Blocks.PURPUR_PILLAR, Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_OAK = registerBlock("oak_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_OAK_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BIRCH = registerBlock("birch_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_BIRCH_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_SPRUCE = registerBlock("spruce_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_SPRUCE_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_JUNGLE = registerBlock("jungle_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_JUNGLE_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ACACIA = registerBlock("acacia_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_ACACIA_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_DARK_OAK = registerBlock("dark_oak_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_DARK_OAK_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHERRY = registerBlock("cherry_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_CHERRY_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MANGROVE = registerBlock("mangrove_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_MANGROVE_LOG, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BAMBOO = registerBlock("bamboo_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_BAMBOO_BLOCK, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_CRIMSON = registerBlock("crimson_plate_block",
            () -> new PlateBlock(Blocks.CRIMSON_STEM, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_WARPED = registerBlock("warped_plate_block",
            () -> new PlateBlock(Blocks.WARPED_STEM, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_TERRACOTTA = registerBlock("terracotta_plate_block",
            () -> new PlateBlock(Blocks.TERRACOTTA, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_WHITE_TERRACOTTA = registerBlock("white_terracotta_plate_block",
            () -> new PlateBlock(Blocks.WHITE_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ORANGE_TERRACOTTA = registerBlock("orange_terracotta_plate_block",
            () -> new PlateBlock(Blocks.ORANGE_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MAGENTA_TERRACOTTA = registerBlock("magenta_terracotta_plate_block",
            () -> new PlateBlock(Blocks.MAGENTA_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA = registerBlock("light_blue_terracotta_plate_block",
            () -> new PlateBlock(Blocks.LIGHT_BLUE_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_YELLOW_TERRACOTTA = registerBlock("yellow_terracotta_plate_block",
            () -> new PlateBlock(Blocks.YELLOW_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIME_TERRACOTTA = registerBlock("lime_terracotta_plate_block",
            () -> new PlateBlock(Blocks.LIME_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PINK_TERRACOTTA = registerBlock("pink_terracotta_plate_block",
            () -> new PlateBlock(Blocks.PINK_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GRAY_TERRACOTTA = registerBlock("gray_terracotta_plate_block",
            () -> new PlateBlock(Blocks.GRAY_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA = registerBlock("light_gray_terracotta_plate_block",
            () -> new PlateBlock(Blocks.LIGHT_GRAY_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CYAN_TERRACOTTA = registerBlock("cyan_terracotta_plate_block",
            () -> new PlateBlock(Blocks.CYAN_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PURPLE_TERRACOTTA = registerBlock("purple_terracotta_plate_block",
            () -> new PlateBlock(Blocks.PURPLE_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLUE_TERRACOTTA = registerBlock("blue_terracotta_plate_block",
            () -> new PlateBlock(Blocks.BLUE_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BROWN_TERRACOTTA = registerBlock("brown_terracotta_plate_block",
            () -> new PlateBlock(Blocks.BROWN_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GREEN_TERRACOTTA = registerBlock("green_terracotta_plate_block",
            () -> new PlateBlock(Blocks.GREEN_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_RED_TERRACOTTA = registerBlock("red_terracotta_plate_block",
            () -> new PlateBlock(Blocks.RED_TERRACOTTA, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLACK_TERRACOTTA = registerBlock("black_terracotta_plate_block",
            () -> new PlateBlock(Blocks.BLACK_TERRACOTTA, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_WHITE_CONCRETE = registerBlock("white_concrete_plate_block",
            () -> new PlateBlock(Blocks.WHITE_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ORANGE_CONCRETE = registerBlock("orange_concrete_plate_block",
            () -> new PlateBlock(Blocks.ORANGE_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MAGENTA_CONCRETE = registerBlock("magenta_concrete_plate_block",
            () -> new PlateBlock(Blocks.MAGENTA_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_BLUE_CONCRETE = registerBlock("light_blue_concrete_plate_block",
            () -> new PlateBlock(Blocks.LIGHT_BLUE_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_YELLOW_CONCRETE = registerBlock("yellow_concrete_plate_block",
            () -> new PlateBlock(Blocks.YELLOW_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIME_CONCRETE = registerBlock("lime_concrete_plate_block",
            () -> new PlateBlock(Blocks.LIME_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PINK_CONCRETE = registerBlock("pink_concrete_plate_block",
            () -> new PlateBlock(Blocks.PINK_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GRAY_CONCRETE = registerBlock("gray_concrete_plate_block",
            () -> new PlateBlock(Blocks.GRAY_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_GRAY_CONCRETE = registerBlock("light_gray_concrete_plate_block",
            () -> new PlateBlock(Blocks.LIGHT_GRAY_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CYAN_CONCRETE = registerBlock("cyan_concrete_plate_block",
            () -> new PlateBlock(Blocks.CYAN_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PURPLE_CONCRETE = registerBlock("purple_concrete_plate_block",
            () -> new PlateBlock(Blocks.PURPLE_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLUE_CONCRETE = registerBlock("blue_concrete_plate_block",
            () -> new PlateBlock(Blocks.BLUE_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BROWN_CONCRETE = registerBlock("brown_concrete_plate_block",
            () -> new PlateBlock(Blocks.BROWN_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GREEN_CONCRETE = registerBlock("green_concrete_plate_block",
            () -> new PlateBlock(Blocks.GREEN_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_RED_CONCRETE = registerBlock("red_concrete_plate_block",
            () -> new PlateBlock(Blocks.RED_CONCRETE, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLACK_CONCRETE = registerBlock("black_concrete_plate_block",
            () -> new PlateBlock(Blocks.BLACK_CONCRETE,getDefaultPlateProperties()));

//    public static final RegistryObject<Block> PLATE_BLOCK = registerBlock("plate_block",
//            () -> new PlateBlock(Blocks.STONE, Ingredient.of(), 1, getDefaultPlateProperties()));

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
