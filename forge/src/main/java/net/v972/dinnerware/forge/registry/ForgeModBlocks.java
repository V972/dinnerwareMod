package net.v972.dinnerware.forge.registry;

import com.mojang.datafixers.util.Pair;
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
import net.v972.dinnerware.registry.PlateDefinition;

import java.util.stream.Stream;


public class ForgeModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, DinnerwareCommon.MOD_ID);

    // ========================================

    public static final RegistryObject<Block> PLATE_BLOCK_BEDROCK = registerPlateBlock(PlateDefinition.BEDROCK,
        BlockBehaviour.Properties.copy(PlateDefinition.BEDROCK.material())
            .isValidSpawn((state, getter, pos, entityType) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
            .isSuffocating((state, level, pos) -> false)
            .isViewBlocking((state, level, pos) -> false)
            .pushReaction(PushReaction.BLOCK)
    );

    public static final RegistryObject<Block> PLATE_BLOCK_QUARTZ = registerPlateBlock(PlateDefinition.QUARTZ,
            Ingredient.of(Tags.Items.GEMS_QUARTZ), 1);
    public static final RegistryObject<Block> PLATE_BLOCK_CHISELED_QUARTZ = registerPlateBlock(PlateDefinition.CHISELED_QUARTZ);

    public static final RegistryObject<Block> PLATE_BLOCK_IRON = registerPlateBlock(PlateDefinition.IRON,
            Ingredient.of(Tags.Items.INGOTS_IRON), 1);
    public static final RegistryObject<Block> PLATE_BLOCK_GOLD = registerPlateBlock(PlateDefinition.GOLD,
            Ingredient.of(Tags.Items.INGOTS_GOLD), 1);
    public static final RegistryObject<Block> PLATE_BLOCK_DIAMOND = registerPlateBlock(PlateDefinition.DIAMOND,
            Ingredient.of(Tags.Items.GEMS_DIAMOND), 1);

    public static final RegistryObject<Block> PLATE_BLOCK_OBSIDIAN = registerPlateBlock(PlateDefinition.OBSIDIAN);

    public static final RegistryObject<Block> PLATE_BLOCK_POLISHED_BLACKSTONE = registerPlateBlock(PlateDefinition.POLISHED_BLACKSTONE);
    public static final RegistryObject<Block> PLATE_BLOCK_CHISELED_NETHER_BRICKS = registerPlateBlock(PlateDefinition.CHISELED_NETHER_BRICKS,
            Ingredient.of(Tags.Items.INGOTS_NETHER_BRICK), 1);

    public static final RegistryObject<Block> PLATE_BLOCK_PURPUR = registerPlateBlock(PlateDefinition.PURPUR,
            Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1);

    public static final RegistryObject<Block> PLATE_BLOCK_OAK = registerPlateBlock(PlateDefinition.OAK);
    public static final RegistryObject<Block> PLATE_BLOCK_BIRCH = registerPlateBlock(PlateDefinition.BIRCH);
    public static final RegistryObject<Block> PLATE_BLOCK_SPRUCE = registerPlateBlock(PlateDefinition.SPRUCE);
    public static final RegistryObject<Block> PLATE_BLOCK_JUNGLE = registerPlateBlock(PlateDefinition.JUNGLE);
    public static final RegistryObject<Block> PLATE_BLOCK_ACACIA = registerPlateBlock(PlateDefinition.ACACIA);
    public static final RegistryObject<Block> PLATE_BLOCK_DARK_OAK = registerPlateBlock(PlateDefinition.DARK_OAK);
    public static final RegistryObject<Block> PLATE_BLOCK_CHERRY = registerPlateBlock(PlateDefinition.CHERRY);
    public static final RegistryObject<Block> PLATE_BLOCK_MANGROVE = registerPlateBlock(PlateDefinition.MANGROVE);
    public static final RegistryObject<Block> PLATE_BLOCK_BAMBOO = registerPlateBlock(PlateDefinition.BAMBOO);

    public static final RegistryObject<Block> PLATE_BLOCK_CRIMSON = registerPlateBlock(PlateDefinition.CRIMSON);
    public static final RegistryObject<Block> PLATE_BLOCK_WARPED = registerPlateBlock(PlateDefinition.WARPED);

    public static final RegistryObject<Block> PLATE_BLOCK_TERRACOTTA =  registerPlateBlock(PlateDefinition.TERRACOTTA);

    public static final RegistryObject<Block> PLATE_BLOCK_WHITE_TERRACOTTA = registerPlateBlock(PlateDefinition.WHITE_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_ORANGE_TERRACOTTA = registerPlateBlock(PlateDefinition.ORANGE_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_MAGENTA_TERRACOTTA = registerPlateBlock(PlateDefinition.MAGENTA_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_BLUE_TERRACOTTA = registerPlateBlock(PlateDefinition.LIGHT_BLUE_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_YELLOW_TERRACOTTA = registerPlateBlock(PlateDefinition.YELLOW_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_LIME_TERRACOTTA = registerPlateBlock(PlateDefinition.LIME_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_PINK_TERRACOTTA = registerPlateBlock(PlateDefinition.PINK_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_GRAY_TERRACOTTA = registerPlateBlock(PlateDefinition.GRAY_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_GRAY_TERRACOTTA = registerPlateBlock(PlateDefinition.LIGHT_GRAY_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_CYAN_TERRACOTTA = registerPlateBlock(PlateDefinition.CYAN_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_PURPLE_TERRACOTTA = registerPlateBlock(PlateDefinition.PURPLE_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_BLUE_TERRACOTTA = registerPlateBlock(PlateDefinition.BLUE_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_BROWN_TERRACOTTA = registerPlateBlock(PlateDefinition.BROWN_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_GREEN_TERRACOTTA = registerPlateBlock(PlateDefinition.GREEN_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_RED_TERRACOTTA = registerPlateBlock(PlateDefinition.RED_TERRACOTTA);
    public static final RegistryObject<Block> PLATE_BLOCK_BLACK_TERRACOTTA = registerPlateBlock(PlateDefinition.BLACK_TERRACOTTA);

    public static final RegistryObject<Block> PLATE_BLOCK_WHITE_CONCRETE = registerPlateBlock(PlateDefinition.WHITE_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_ORANGE_CONCRETE = registerPlateBlock(PlateDefinition.ORANGE_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_MAGENTA_CONCRETE = registerPlateBlock(PlateDefinition.MAGENTA_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_BLUE_CONCRETE = registerPlateBlock(PlateDefinition.LIGHT_BLUE_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_YELLOW_CONCRETE = registerPlateBlock(PlateDefinition.YELLOW_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_LIME_CONCRETE = registerPlateBlock(PlateDefinition.LIME_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_PINK_CONCRETE = registerPlateBlock(PlateDefinition.PINK_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_GRAY_CONCRETE = registerPlateBlock(PlateDefinition.GRAY_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_LIGHT_GRAY_CONCRETE = registerPlateBlock(PlateDefinition.LIGHT_GRAY_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_CYAN_CONCRETE = registerPlateBlock(PlateDefinition.CYAN_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_PURPLE_CONCRETE = registerPlateBlock(PlateDefinition.PURPLE_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_BLUE_CONCRETE = registerPlateBlock(PlateDefinition.BLUE_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_BROWN_CONCRETE = registerPlateBlock(PlateDefinition.BROWN_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_GREEN_CONCRETE = registerPlateBlock(PlateDefinition.GREEN_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_RED_CONCRETE = registerPlateBlock(PlateDefinition.RED_CONCRETE);
    public static final RegistryObject<Block> PLATE_BLOCK_BLACK_CONCRETE = registerPlateBlock(PlateDefinition.BLACK_CONCRETE);

    // ========================================

    public static Block[] getKnownPlateBlocksArray() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new);
    }

    public static Iterable<Block> getKnownPlateBlocksIterable() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    public static Iterable<Pair<Block, Ingredient>> getTerracottaPlateBlocksDyeingMap() {
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

    private static RegistryObject<Block> registerPlateBlock(PlateDefinition definition) {
        return BLOCKS.register(definition.block(),
            () -> new PlateBlock(definition.material(), getDefaultPlateProperties()));
    }

    private static RegistryObject<Block> registerPlateBlock(
        PlateDefinition definition,
        Ingredient pCraftMaterial,
        int pCraftingAmount
    ) {
        return BLOCKS.register(definition.block(),
            () -> new PlateBlock(
                definition.material(),
                pCraftMaterial,
                pCraftingAmount,
                getDefaultPlateProperties()));
    }

    private static RegistryObject<Block> registerPlateBlock(
        PlateDefinition definition,
        BlockBehaviour.Properties properties
    ) {
        return BLOCKS.register(definition.block(),
            () -> new PlateBlock(definition.material(), properties));
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
