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
import net.v972.dinnerware.DinnerwareCommon;
import net.v972.dinnerware.DinnerwareConstants;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.forge.registry.ForgeModItems;
import net.v972.dinnerware.item.custom.PlateBlockBlockItem;
import net.v972.dinnerware.item.custom.TrayItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DinnerwareCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ForgeModItems.ICON);

        for (PlateBlockBlockItem item : ForgeModItems.getKnownPlateItemsArray()) {
            Block material = ((PlateBlock) item.getBlock()).MATERIAL;
            ResourceLocation finalTexture = DinnerwareDatagenHelper.getTextureForModel(material);
            getBuilder(item.toString())
                .parent(new ModelFile.ExistingModelFile(
                    DinnerwareConstants.id(ITEM_FOLDER + "/plate"),
                    existingFileHelper
                )
            ).texture("particle", finalTexture);
        }

        for (TrayItem item : ForgeModItems.getTrayItemsArray()) {
            ResourceLocation finalTexture = DinnerwareDatagenHelper.getTextureForModel(item.MATERIAL);
            ResourceLocation parentModelLoc = DinnerwareConstants.id(ITEM_FOLDER + "/tray");
            getBuilder(item.toString())
                .parent(new ModelFile.ExistingModelFile(parentModelLoc, existingFileHelper))
                .texture("0", finalTexture)
                .texture("particle", finalTexture);
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
            ResourceLocation.fromNamespaceAndPath("minecraft","item/generated")).texture("layer0",
                DinnerwareConstants.id("item/" + item.getId().getPath())
        );
    }
}
