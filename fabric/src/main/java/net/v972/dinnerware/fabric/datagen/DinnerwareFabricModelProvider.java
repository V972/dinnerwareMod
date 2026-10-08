package net.v972.dinnerware.fabric.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.Registry;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public final class DinnerwareFabricModelProvider implements DataProvider {

    private final Path output;

    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create();

    public DinnerwareFabricModelProvider(FabricDataGenerator dataGenerator) {
        this.output = dataGenerator.getOutputFolder();
    }

    @Override
    public @NotNull String getName() {
        return "Common Dinnerware Models and Blockstates";
    }

    @Override
    public void run(@NotNull HashCache output) throws IOException {

        // plate block models
        for (Block block : FabricModBlocks.getKnownPlateBlocksIterable()) {
            PlateBlock plateBlock = (PlateBlock) block;

            ResourceLocation blockId = Registry.BLOCK.getKey(plateBlock);

            ResourceLocation materialTexture = getTextureForMaterial(plateBlock.MATERIAL);
            save(
                output,
                createPlateBlockModel(plateBlock, materialTexture),
                resourcePath("models/block", blockId)
            );

            save(
                output,
                createPlateBlockState(blockId),
                resourcePath("blockstates", blockId)
            );
        }

        // plate item models
        for (PlateBlockBlockItem item : FabricModItems.getKnownPlateItemsArray()) {
            ResourceLocation itemId = Registry.ITEM.getKey(item);
            PlateBlock plateBlock = (PlateBlock)item.getBlock();
            save(
                output,
                createPlateItemModel(getTextureForMaterial(plateBlock.MATERIAL)),
                resourcePath("models/item", itemId)
            );
        }

        // tray models
        for (Item item : FabricModItems.getTrayItemsArray()) {
            TrayItem tray = (TrayItem) item;
            ResourceLocation itemId = Registry.ITEM.getKey(tray);
            save(
                output,
                createTrayItemModel(getTextureForMaterial(tray.MATERIAL)),
                resourcePath("models/item", itemId)
            );
        }

        // icon item
        ResourceLocation iconId = Registry.ITEM.getKey(FabricModItems.icon());
        save(
            output,
            createSimpleItemModel(iconId),
            resourcePath("models/item", iconId)
        );
    }

    private static JsonObject createPlateBlockModel(PlateBlock plateBlock, ResourceLocation texture) {
        JsonObject json = new JsonObject();

        json.addProperty(
            "parent",
            DinnerwareConstants.id(
                plateBlock.MATERIAL instanceof RotatedPillarBlock &&
                plateBlock.MATERIAL != Blocks.QUARTZ_BLOCK
                    ? "block/plate_block_column"
                    : "block/plate_block"
            ).toString()
        );

        JsonObject textures = new JsonObject();
        textures.addProperty("0", texture.toString());
        textures.addProperty("particle", texture.toString());

        json.add("textures", textures);

        return json;
    }

    private static JsonObject createPlateBlockState(ResourceLocation blockId) {
        JsonObject json = new JsonObject();
        JsonObject variants = new JsonObject();

        addVariant(variants, "facing=south", blockId, 0);
        addVariant(variants, "facing=west", blockId, 90);
        addVariant(variants, "facing=north", blockId, 180);
        addVariant(variants, "facing=east", blockId, 270);

        json.add("variants", variants);

        return json;
    }

    private static void addVariant(
        JsonObject variants, String key,
        ResourceLocation blockId, int rotationY) {

        JsonObject variant = new JsonObject();

        variant.addProperty(
            "model",
            new ResourceLocation(blockId.getNamespace(), "block/" + blockId.getPath()).toString()
        );

        if (rotationY != 0) variant.addProperty("y", rotationY);

        variants.add(key, variant);
    }

    private static JsonObject createPlateItemModel(ResourceLocation texture) {
        JsonObject json = new JsonObject();

        json.addProperty(
            "parent",
            DinnerwareConstants.id("item/plate").toString()
        );

        JsonObject textures = new JsonObject();
        textures.addProperty("particle", texture.toString());

        json.add("textures", textures);

        return json;
    }

    private static JsonObject createTrayItemModel(ResourceLocation texture) {
        JsonObject json = new JsonObject();

        json.addProperty(
            "parent",
            DinnerwareConstants.id("item/tray").toString()
        );

        JsonObject textures = new JsonObject();
        textures.addProperty("0", texture.toString());
        textures.addProperty("particle", texture.toString());

        json.add("textures", textures);

        return json;
    }

    private static JsonObject createSimpleItemModel(ResourceLocation itemId) {
        JsonObject json = new JsonObject();

        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty(
            "layer0",
            DinnerwareConstants.id("item/" + itemId.getPath()).toString()
        );

        json.add("textures", textures);

        return json;
    }

    private static ResourceLocation getTextureForMaterial(Block material) {
        ResourceLocation materialId = Registry.BLOCK.getKey(material);

        if (materialId == Registry.BLOCK.getDefaultKey())
            throw new IllegalStateException("Material block is not registered: " + material);

        boolean useTopTexture =
            material == Blocks.QUARTZ_BLOCK ||
            material instanceof RotatedPillarBlock;

        return new ResourceLocation(
            materialId.getNamespace(),
            "block/" + materialId.getPath() + (useTopTexture ? "_top" : "")
        );
    }

    private Path resourcePath(String folder, ResourceLocation id) {
        return output
            .resolve("assets")
            .resolve(id.getNamespace())
            .resolve(folder)
            .resolve(id.getPath() + ".json");
    }

    private static void save(HashCache output, JsonObject json, Path path) throws IOException {
        DataProvider.save(GSON, output, json, path);
    }
}