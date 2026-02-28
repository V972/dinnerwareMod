package net.v972.dinnerware.block;

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
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;


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

    public static final RegistryObject<Block> PLATE_BLOCK_OAK = registerBlock("oak_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_OAK_LOG, Ingredient.of(Items.STRIPPED_OAK_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BIRCH = registerBlock("birch_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_BIRCH_LOG, Ingredient.of(Items.STRIPPED_BIRCH_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_SPRUCE = registerBlock("spruce_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_SPRUCE_LOG, Ingredient.of(Items.STRIPPED_SPRUCE_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_JUNGLE = registerBlock("jungle_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_JUNGLE_LOG, Ingredient.of(Items.STRIPPED_JUNGLE_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_ACACIA = registerBlock("acacia_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_ACACIA_LOG, Ingredient.of(Items.STRIPPED_ACACIA_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_DARK_OAK = registerBlock("dark_oak_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_DARK_OAK_LOG, Ingredient.of(Items.STRIPPED_DARK_OAK_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_CHERRY = registerBlock("cherry_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_CHERRY_LOG, Ingredient.of(Items.STRIPPED_CHERRY_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_MANGROVE = registerBlock("mangrove_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_MANGROVE_LOG, Ingredient.of(Items.STRIPPED_MANGROVE_LOG), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_BAMBOO = registerBlock("bamboo_plate_block",
            () -> new PlateBlock(Blocks.STRIPPED_BAMBOO_BLOCK, Ingredient.of(Items.STRIPPED_BAMBOO_BLOCK), 3, getDefaultPlateProperties()));

    public static final RegistryObject<Block> PLATE_BLOCK_CRIMSON = registerBlock("crimson_plate_block",
            () -> new PlateBlock(Blocks.CRIMSON_STEM, Ingredient.of(Items.CRIMSON_STEM), 3, getDefaultPlateProperties()));
    public static final RegistryObject<Block> PLATE_BLOCK_WARPED = registerBlock("warped_plate_block",
            () -> new PlateBlock(Blocks.WARPED_STEM, Ingredient.of(Items.WARPED_STEM), 3, getDefaultPlateProperties()));

//    public static final RegistryObject<Block> PLATE_BLOCK = registerBlock("plate_block",
//            () -> new PlateBlock(Blocks.STONE, Ingredient.of(), 1, getDefaultPlateProperties()));

    // ========================================

    public static Block[] getKnownBlocksArray() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new);
    }

    public static Iterable<Block> getKnownBlocks() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
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
                //.noOcclusion()
        ;
    }

    // ========================================

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
