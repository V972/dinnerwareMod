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

    public static final RegistryObject<Block> PLATE_BLOCK_QUARTZ = registerBlock("quartz_plate_block",
            () -> new PlateBlock(Blocks.QUARTZ_BLOCK, Ingredient.of(Tags.Items.GEMS_QUARTZ), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_IRON = registerBlock("iron_plate_block",
            () -> new PlateBlock(Blocks.IRON_BLOCK, Ingredient.of(Tags.Items.INGOTS_IRON), 1, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GOLD = registerBlock("gold_plate_block",
            () -> new PlateBlock(Blocks.GOLD_BLOCK, Ingredient.of(Tags.Items.INGOTS_GOLD), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_DIAMOND = registerBlock("diamond_plate_block",
            () -> new PlateBlock(Blocks.DIAMOND_BLOCK, Ingredient.of(Tags.Items.GEMS_DIAMOND), 1, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_OBSIDIAN = registerBlock("obsidian_plate_block",
            () -> new PlateBlock(Blocks.OBSIDIAN, Ingredient.of(Items.OBSIDIAN), 6, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_OAK = registerBlock("oak_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_OAK_LOG, Ingredient.of(Items.STRIPPED_OAK_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BIRCH = registerBlock("birch_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_BIRCH_LOG, Ingredient.of(Items.STRIPPED_BIRCH_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_SPRUCE = registerBlock("spruce_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_SPRUCE_LOG, Ingredient.of(Items.STRIPPED_SPRUCE_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_JUNGLE = registerBlock("jungle_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_JUNGLE_LOG, Ingredient.of(Items.STRIPPED_JUNGLE_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ACACIA = registerBlock("acacia_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_ACACIA_LOG, Ingredient.of(Items.STRIPPED_ACACIA_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_DARK_OAK = registerBlock("dark_oak_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_DARK_OAK_LOG, Ingredient.of(Items.STRIPPED_DARK_OAK_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHERRY = registerBlock("cherry_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_CHERRY_LOG, Ingredient.of(Items.STRIPPED_CHERRY_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MANGROVE = registerBlock("mangrove_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_MANGROVE_LOG, Ingredient.of(Items.STRIPPED_MANGROVE_LOG), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BAMBOO = registerBlock("bamboo_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_BAMBOO_BLOCK, Ingredient.of(Items.STRIPPED_BAMBOO_BLOCK), 6, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_CRIMSON = registerBlock("crimson_plate_block",
            () -> new PlateBlock(Blocks.CRIMSON_STEM, Ingredient.of(Items.CRIMSON_STEM), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_WARPED = registerBlock("warped_plate_block",
            () -> new PlateBlock(Blocks.WARPED_STEM, Ingredient.of(Items.WARPED_STEM), 6, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_TERRACOTTA = registerBlock("terracotta_plate_block",
            () -> new PlateBlock(Blocks.TERRACOTTA, Ingredient.of(Items.TERRACOTTA), 6, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_WHITE_TERRACOTTA = registerBlock("white_terracotta_plate_block",
            () -> new PlateBlock(Blocks.WHITE_TERRACOTTA, Ingredient.of(Items.WHITE_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ORANGE_TERRACOTTA = registerBlock("orange_terracotta_plate_block",
            () -> new PlateBlock(Blocks.ORANGE_TERRACOTTA, Ingredient.of(Items.ORANGE_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MAGENTA_TERRACOTTA = registerBlock("magenta_terracotta_plate_block",
            () -> new PlateBlock(Blocks.MAGENTA_TERRACOTTA, Ingredient.of(Items.MAGENTA_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA = registerBlock("light_blue_terracotta_plate_block",
            () -> new PlateBlock(Blocks.LIGHT_BLUE_TERRACOTTA, Ingredient.of(Items.LIGHT_BLUE_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_YELLOW_TERRACOTTA = registerBlock("yellow_terracotta_plate_block",
            () -> new PlateBlock(Blocks.YELLOW_TERRACOTTA, Ingredient.of(Items.YELLOW_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIME_TERRACOTTA = registerBlock("lime_terracotta_plate_block",
            () -> new PlateBlock(Blocks.LIME_TERRACOTTA, Ingredient.of(Items.LIME_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PINK_TERRACOTTA = registerBlock("pink_terracotta_plate_block",
            () -> new PlateBlock(Blocks.PINK_TERRACOTTA, Ingredient.of(Items.PINK_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GRAY_TERRACOTTA = registerBlock("gray_terracotta_plate_block",
            () -> new PlateBlock(Blocks.GRAY_TERRACOTTA, Ingredient.of(Items.GRAY_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA = registerBlock("light_gray_terracotta_plate_block",
            () -> new PlateBlock(Blocks.LIGHT_GRAY_TERRACOTTA, Ingredient.of(Items.LIGHT_GRAY_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CYAN_TERRACOTTA = registerBlock("cyan_terracotta_plate_block",
            () -> new PlateBlock(Blocks.CYAN_TERRACOTTA, Ingredient.of(Items.CYAN_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_PURPLE_TERRACOTTA = registerBlock("purple_terracotta_plate_block",
            () -> new PlateBlock(Blocks.PURPLE_TERRACOTTA, Ingredient.of(Items.PURPLE_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLUE_TERRACOTTA = registerBlock("blue_terracotta_plate_block",
            () -> new PlateBlock(Blocks.BLUE_TERRACOTTA, Ingredient.of(Items.BLUE_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BROWN_TERRACOTTA = registerBlock("brown_terracotta_plate_block",
            () -> new PlateBlock(Blocks.BROWN_TERRACOTTA, Ingredient.of(Items.BROWN_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_GREEN_TERRACOTTA = registerBlock("green_terracotta_plate_block",
            () -> new PlateBlock(Blocks.GREEN_TERRACOTTA, Ingredient.of(Items.GREEN_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_RED_TERRACOTTA = registerBlock("red_terracotta_plate_block",
            () -> new PlateBlock(Blocks.RED_TERRACOTTA, Ingredient.of(Items.RED_TERRACOTTA), 6, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BLACK_TERRACOTTA = registerBlock("black_terracotta_plate_block",
            () -> new PlateBlock(Blocks.BLACK_TERRACOTTA, Ingredient.of(Items.BLACK_TERRACOTTA), 6, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_BEDROCK = registerBlock("bedrock_plate_block",
            () -> new PlateBlock(Blocks.BEDROCK, Ingredient.of(Items.BEDROCK), 6,
                    BlockBehaviour.Properties.copy(Blocks.BEDROCK)
                        .isValidSpawn((state, getter, pos, entityType) -> false)
                        .isRedstoneConductor((state, level, pos) -> false)
                        .isSuffocating((state, level, pos) -> false)
                        .isViewBlocking((state, level, pos) -> false)
                        .pushReaction(PushReaction.BLOCK)
            ));

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
