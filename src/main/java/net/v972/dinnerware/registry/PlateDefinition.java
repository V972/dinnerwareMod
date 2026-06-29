package net.v972.dinnerware.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public enum PlateDefinition {
    BEDROCK("bedrock", () -> Blocks.BEDROCK),
    QUARTZ("quartz", () -> Blocks.QUARTZ_BLOCK),
    CHISELED_QUARTZ("chiseled_quartz", () -> Blocks.CHISELED_QUARTZ_BLOCK),
    IRON("iron", () -> Blocks.IRON_BLOCK),
    GOLD("gold", () -> Blocks.GOLD_BLOCK),
    DIAMOND("diamond", () -> Blocks.DIAMOND_BLOCK),
    OBSIDIAN("obsidian", () -> Blocks.OBSIDIAN),
    POLISHED_BLACKSTONE("polished_blackstone", () -> Blocks.POLISHED_BLACKSTONE),
    CHISELED_NETHER_BRICKS("chiseled_nether_bricks", () -> Blocks.CHISELED_NETHER_BRICKS),
    PURPUR("purpur", () -> Blocks.PURPUR_PILLAR),

    OAK("oak", () -> Blocks.STRIPPED_OAK_LOG),
    BIRCH("birch", () -> Blocks.STRIPPED_BIRCH_LOG),
    SPRUCE("spruce", () -> Blocks.STRIPPED_SPRUCE_LOG),
    JUNGLE("jungle", () -> Blocks.STRIPPED_JUNGLE_LOG),
    ACACIA("acacia", () -> Blocks.STRIPPED_ACACIA_LOG),
    DARK_OAK("dark_oak", () -> Blocks.STRIPPED_DARK_OAK_LOG),
    CHERRY("cherry", () -> Blocks.STRIPPED_CHERRY_LOG),
    MANGROVE("mangrove", () -> Blocks.STRIPPED_MANGROVE_LOG),
    BAMBOO("bamboo", () -> Blocks.STRIPPED_BAMBOO_BLOCK),
    CRIMSON("crimson", () -> Blocks.STRIPPED_CRIMSON_STEM),
    WARPED("warped", () -> Blocks.STRIPPED_WARPED_STEM),

    TERRACOTTA("terracotta", () -> Blocks.TERRACOTTA),
    WHITE_TERRACOTTA("white_terracotta", () -> Blocks.WHITE_TERRACOTTA),
    ORANGE_TERRACOTTA("orange_terracotta", () -> Blocks.ORANGE_TERRACOTTA),
    MAGENTA_TERRACOTTA("magenta_terracotta", () -> Blocks.MAGENTA_TERRACOTTA),
    LIGHT_BLUE_TERRACOTTA("light_blue_terracotta", () -> Blocks.LIGHT_BLUE_TERRACOTTA),
    YELLOW_TERRACOTTA("yellow_terracotta", () -> Blocks.YELLOW_TERRACOTTA),
    LIME_TERRACOTTA("lime_terracotta", () -> Blocks.LIME_TERRACOTTA),
    PINK_TERRACOTTA("pink_terracotta", () -> Blocks.PINK_TERRACOTTA),
    GRAY_TERRACOTTA("gray_terracotta", () -> Blocks.GRAY_TERRACOTTA),
    LIGHT_GRAY_TERRACOTTA("light_gray_terracotta", () -> Blocks.LIGHT_GRAY_TERRACOTTA),
    CYAN_TERRACOTTA("cyan_terracotta", () -> Blocks.CYAN_TERRACOTTA),
    PURPLE_TERRACOTTA("purple_terracotta", () -> Blocks.PURPLE_TERRACOTTA),
    BLUE_TERRACOTTA("blue_terracotta", () -> Blocks.BLUE_TERRACOTTA),
    BROWN_TERRACOTTA("brown_terracotta", () -> Blocks.BROWN_TERRACOTTA),
    GREEN_TERRACOTTA("green_terracotta", () -> Blocks.GREEN_TERRACOTTA),
    RED_TERRACOTTA("red_terracotta", () -> Blocks.RED_TERRACOTTA),
    BLACK_TERRACOTTA("black_terracotta", () -> Blocks.BLACK_TERRACOTTA),

    WHITE_CONCRETE("white_concrete", () -> Blocks.WHITE_CONCRETE),
    ORANGE_CONCRETE("orange_concrete", () -> Blocks.ORANGE_CONCRETE),
    MAGENTA_CONCRETE("magenta_concrete", () -> Blocks.MAGENTA_CONCRETE),
    LIGHT_BLUE_CONCRETE("light_blue_concrete", () -> Blocks.LIGHT_BLUE_CONCRETE),
    YELLOW_CONCRETE("yellow_concrete", () -> Blocks.YELLOW_CONCRETE),
    LIME_CONCRETE("lime_concrete", () -> Blocks.LIME_CONCRETE),
    PINK_CONCRETE("pink_concrete", () -> Blocks.PINK_CONCRETE),
    GRAY_CONCRETE("gray_concrete", () -> Blocks.GRAY_CONCRETE),
    LIGHT_GRAY_CONCRETE("light_gray_concrete", () -> Blocks.LIGHT_GRAY_CONCRETE),
    CYAN_CONCRETE("cyan_concrete", () -> Blocks.CYAN_CONCRETE),
    PURPLE_CONCRETE("purple_concrete", () -> Blocks.PURPLE_CONCRETE),
    BLUE_CONCRETE("blue_concrete", () -> Blocks.BLUE_CONCRETE),
    BROWN_CONCRETE("brown_concrete", () -> Blocks.BROWN_CONCRETE),
    GREEN_CONCRETE("green_concrete", () -> Blocks.GREEN_CONCRETE),
    RED_CONCRETE("red_concrete", () -> Blocks.RED_CONCRETE),
    BLACK_CONCRETE("black_concrete", () -> Blocks.BLACK_CONCRETE);

    private final String name;
    private final Supplier<Block> materialBlock;

    PlateDefinition(String name, Supplier<Block> materialBlock) {
        this.name = name;
        this.materialBlock = materialBlock;
    }

    public String item() {
        return name + "_plate";
    }

    public String block() {
        return name + "_plate_block";
    }

    public Block material() {
        return materialBlock.get();
    }
}