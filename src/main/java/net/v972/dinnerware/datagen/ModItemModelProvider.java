package net.v972.dinnerware.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;
import net.v972.dinnerware.util.DinnerwareHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DinnerwareMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.ICON);

        for (PlateBlockBlockItem item : ModItems.getPlateItemsArray()) {
            Block material = ((PlateBlock) item.getBlock()).MATERIAL;
            ResourceLocation finalTexture = DinnerwareHelper.getTextureForModel(material);
            getBuilder(item.toString())
                .parent(new ModelFile.ExistingModelFile(
                    ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, ITEM_FOLDER + "/plate"),
                    existingFileHelper
                )
            ).texture("particle", finalTexture);
        }

        for (TrayItem item : ModItems.getTrayItemsArray()) {
            ResourceLocation finalTexture = DinnerwareHelper.getTextureForModel(item.MATERIAL);
            ResourceLocation parentModelLoc = ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID, ITEM_FOLDER + "/tray");
            getBuilder(item.toString())
                .parent(new ModelFile.ExistingModelFile(parentModelLoc, existingFileHelper))
                .texture("0", finalTexture)
                .texture("particle", finalTexture);
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft","item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
