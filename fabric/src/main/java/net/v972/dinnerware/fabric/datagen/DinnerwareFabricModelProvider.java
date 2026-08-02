package net.v972.dinnerware.fabric.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.datagen.DinnerwareDatagenPaths;
import net.v972.dinnerware.fabric.registry.FabricModBlocks;
import net.v972.dinnerware.fabric.registry.FabricModItems;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class DinnerwareFabricModelProvider implements DataProvider {

    private final PackOutput.PathProvider blockStates;
    private final PackOutput.PathProvider blockModels;
    private final PackOutput.PathProvider itemModels;

    public DinnerwareFabricModelProvider(FabricDataOutput output) {
        FabricDataOutput commonOutput = new FabricDataOutput(
            output.getModContainer(),
            DinnerwareDatagenPaths.commonOutput(),
            output.isStrictValidationEnabled()
        );

        this.blockStates = commonOutput.createPathProvider(
            PackOutput.Target.RESOURCE_PACK,
            "blockstates"
        );

        this.blockModels = commonOutput.createPathProvider(
            PackOutput.Target.RESOURCE_PACK,
            "models/block"
        );

        this.itemModels = commonOutput.createPathProvider(
            PackOutput.Target.RESOURCE_PACK,
            "models/item"
        );
    }

    @Override
    public String getName() {
        return "Common Dinnerware Models and Blockstates";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        List<CompletableFuture<?>> writes = new ArrayList<>();

        // plate block models
        for (Block block : FabricModBlocks.getKnownPlateBlocksIterable()) {
            PlateBlock plateBlock = (PlateBlock) block;

            ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(plateBlock);

            ResourceLocation materialTexture = getTextureForMaterial(plateBlock.MATERIAL);
            writes.add(save(
                output,
                createPlateBlockModel(plateBlock, materialTexture),
                blockModels.json(blockId)
            ));

            writes.add(save(
                output,
                createPlateBlockState(blockId),
                blockStates.json(blockId)
            ));
        }

        // plate item models
        for (PlateBlockBlockItem item : FabricModItems.getKnownPlateItemsArray()) {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
            PlateBlock plateBlock = (PlateBlock)item.getBlock();
            writes.add(save(
                output,
                createPlateItemModel(getTextureForMaterial(plateBlock.MATERIAL)),
                itemModels.json(itemId)
            ));
        }

        // tray models
        for (Item item : FabricModItems.getTrayItemsArray()) {
            TrayItem tray = (TrayItem) item;
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(tray);
            writes.add(save(
                output,
                createTrayItemModel(getTextureForMaterial(tray.MATERIAL)),
                itemModels.json(itemId)
            ));
        }

        // icon item
        ResourceLocation iconId = BuiltInRegistries.ITEM.getKey(FabricModItems.icon());
        writes.add(save(
            output,
            createSimpleItemModel(iconId),
            itemModels.json(iconId)
        ));

        return CompletableFuture.allOf(writes.toArray(CompletableFuture[]::new));
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
        ResourceLocation materialId = BuiltInRegistries.BLOCK.getKey(material);

        if (materialId == BuiltInRegistries.BLOCK.getDefaultKey())
            throw new IllegalStateException("Material block is not registered: " + material);

        boolean useTopTexture =
            material == Blocks.QUARTZ_BLOCK ||
            material instanceof RotatedPillarBlock;

        return new ResourceLocation(
            materialId.getNamespace(),
            "block/" + materialId.getPath() + (useTopTexture ? "_top" : "")
        );
    }

    private static CompletableFuture<?> save(CachedOutput output, JsonObject json, Path path) {
        return DataProvider.saveStable(output, json, path);
    }
}