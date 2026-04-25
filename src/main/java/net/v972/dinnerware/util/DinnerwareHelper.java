package net.v972.dinnerware.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.v972.dinnerware.Config;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.custom.PlateBlock;
import net.v972.dinnerware.item.ModItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DinnerwareHelper {

    /// Pair<horizontal, vertical> | 0.0-1.0
    public static Pair<Double, Double> getBlockHitPos(BlockHitResult pHit, Direction pBlockDir) {
        if (pHit.getDirection() == Direction.UP) {
            Vec3 loc = pHit.getLocation();
            double x_fr = fraction3Places(loc.x); // 0.000f
            double z_fr = fraction3Places(loc.z); // 0.000f

            double x_north_east = x_fr > 0 ? 1 - x_fr : Math.abs(x_fr);
            double x_south_west = x_fr > 0 ? x_fr : 1 - Math.abs(x_fr);

            double z_north_west = z_fr > 0 ? z_fr : 1 - Math.abs(z_fr);
            double z_south_east = z_fr > 0 ? 1 - z_fr : Math.abs(z_fr);

            double hor = switch (pBlockDir) {
                case NORTH -> x_north_east;
                case SOUTH -> x_south_west;
                case EAST -> z_south_east;
                case WEST -> z_north_west;
                default -> 0;
            };
            double vert = switch (pBlockDir) {
                case NORTH -> z_north_west;
                case SOUTH -> z_south_east;
                case EAST -> x_north_east;
                case WEST -> x_south_west;
                default -> 0;
            };

            return new Pair<>(hor, vert);
        }

        return new Pair<>(0.0, 0.0);
    }

    ///  ddddd...ddddd.fffff...fffff -> d.fff
    public static double fraction3Places(double a) { return (double) Math.round((a % 1) * 1000) / 1000; }

    /// "blocks.dinnerware.some_plate" -> "some_plate"
    public static @NotNull String getBlockId(Block block) {
        String[] pathComponents = block.getDescriptionId().split("\\.");
        return pathComponents[pathComponents.length-1];
    }

    public static OptionalInt getFirstNonEmptySlot(NonNullList<ItemStack> pList) {
        return IntStream.range(0, pList.size())
                .filter(i -> !pList.get(i).isEmpty())
                .findFirst();
    }

    public static int getNonEmptySlotsCount(NonNullList<ItemStack> pList) {
        return (int)IntStream
            .range(0, pList.size())
            .filter(i -> !pList.get(i).isEmpty())
            .count();
    }

    public static boolean hasPlateInside(ItemStack pStack) {
        if (!pStack.is(ModTags.Items.PLATES)) return false;

        CompoundTag nbt = pStack.getTag();
        if (nbt == null || nbt.isEmpty()) return false;

        nbt = nbt.getCompound("BlockEntityTag").getCompound("Inventory");

        if (nbt.isEmpty() || nbt.contains("Items")) return false;

        ListTag listTag = nbt.getList("Items", 10);
        Set<Item> plateItemsSet =
            listTag.stream()
                .map(tag -> ItemStack.of((CompoundTag)tag).getItem())
                .collect(Collectors.toSet());
        Set<Item> allPlatesSet = ModItems.getPlateItemsSet();
        plateItemsSet.retainAll(allPlatesSet);
        return !plateItemsSet.isEmpty();
    }

    public static NonNullList<ItemStack> plateContentFromNBT(CompoundTag pTag) {
        NonNullList<ItemStack> result = NonNullList.withSize(3, ItemStack.EMPTY);

        if (pTag != null) {
            CompoundTag nbt = pTag.getCompound("BlockEntityTag").getCompound("Inventory");

            if (nbt.isEmpty()) return result;

            if (nbt.contains("Size", Tag.TAG_INT))
                result = NonNullList.withSize(nbt.getInt("Size"), ItemStack.EMPTY);
            ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++)
            {
                CompoundTag itemTags = tagList.getCompound(i);
                int slot = itemTags.getInt("Slot");

                if (slot >= 0 && slot < result.size())
                {
                    result.set(slot, ItemStack.of(itemTags));
                }
            }
        }

        return result;
    }

    // ===============================================================

    public static NonNullList<ItemStack> trayContentFromNBT(CompoundTag pTag) {
        if (pTag != null && pTag.contains("Items")) {
            ListTag tagList = pTag.getList("Items", 10);

            int listSize = tagList.size();
            if (listSize == 0) return NonNullList.withSize(64, ItemStack.EMPTY);

            NonNullList<ItemStack> result = NonNullList.withSize(listSize, ItemStack.EMPTY);
            for (int i = 0; i < tagList.size(); i++)
            {
                CompoundTag itemTags = tagList.getCompound(i);
                result.set(listSize - (i + 1), ItemStack.of(itemTags));
            }

            return result;
        }

        return NonNullList.withSize(64, ItemStack.EMPTY);
    }

    // ===============================================================

    public static ModelFile getPlateModelWithMaterial(PlateBlock plateBlock, BlockModelProvider models) {
        ResourceLocation finalTexture = getTextureForModel(plateBlock.MATERIAL);

        ResourceLocation parentModelLoc =
            ResourceLocation.fromNamespaceAndPath(DinnerwareMod.MOD_ID,
                plateBlock.MATERIAL instanceof RotatedPillarBlock &&
                !plateBlock.MATERIAL.getDescriptionId().equals(Blocks.QUARTZ_BLOCK.getDescriptionId())
                    ? "plate_block_column"
                    : "plate_block"
            );

        return models
            .getBuilder(DinnerwareHelper.getBlockId(plateBlock))
            .parent(models.getExistingFile(parentModelLoc))
            .texture("0", finalTexture)
            .texture("particle", finalTexture);
    }

    public static @NotNull ResourceLocation getTextureForModel(Block pMaterial) {
        ResourceLocation materialBlockName = ForgeRegistries.BLOCKS.getKey(pMaterial);

        boolean isColumn =
            pMaterial.getDescriptionId().equals(Blocks.QUARTZ_BLOCK.getDescriptionId()) ||
            pMaterial instanceof RotatedPillarBlock;

        ResourceLocation materialTexture = ResourceLocation
            .fromNamespaceAndPath(
                materialBlockName.getNamespace(),
            ModelProvider.BLOCK_FOLDER + "/" + materialBlockName.getPath());
        ResourceLocation materialTextureTop = materialTexture.withSuffix("_top");

        return isColumn
            ? materialTextureTop
            : materialTexture;
    }

    // ===============================================================

    public static void positionAndRenderTrayItems(PoseStack pPoseStack, MultiBufferSource pBuffer, ItemRenderer pItemRenderer,
                                                  NonNullList<ItemStack> pStacks, Direction pFacing,
                                                  @Nullable Level pLevel, int pLightLevel) {
        float currentY = 0;//-0.05f;

        for (int i = 0; i < pStacks.size(); i++) {
            var stack = pStacks.get(i);
            if (stack.isEmpty()) continue;

            currentY += 0.05f;
            for (int j = 0; j < stack.getCount(); j++) {
                currentY += j == 0 ? 0 : 0.025f;

                pPoseStack.pushPose();

                // Move to the center
                pPoseStack.translate(0.5f, currentY, 0.5f);

                // Rotate based on facing
                switch (pFacing) {
                    case NORTH -> {} // pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
                    case WEST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                    case SOUTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
                    case EAST -> pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
                }

                // Lie flat
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

                // Scale down
                pPoseStack.scale(0.45f, 0.45f, 0.45f);

                pItemRenderer.renderStatic(stack,
                        ItemDisplayContext.FIXED, pLightLevel, OverlayTexture.NO_OVERLAY,
                        pPoseStack, pBuffer, pLevel, i);

                pPoseStack.popPose();
            }
        }
    }

    // ===============================================================

    public static void positionAndRenderPlateItems(PoseStack pPoseStack, MultiBufferSource pBuffer, ItemRenderer pItemRenderer,
                                             NonNullList<ItemStack> pStacks, int pNonEmptyCount, Direction pFacing,
                                             @Nullable Level pLevel, int pLightLevel) {
        switch (pNonEmptyCount) {
            case 1 -> {
                int firstNonEmptySlot = getFirstNonEmptySlot(pStacks).orElse(-1);
                renderItem(
                        pPoseStack, pItemRenderer, pBuffer,
                        pStacks.get(firstNonEmptySlot), pFacing, pLevel, pLightLevel,
                        0.045f,
                        (PoseStack p) -> {}, 1
                );
            }
            case 2 -> positionTwoItems(
                    pPoseStack, pItemRenderer, pBuffer,
                    pStacks, pFacing, pLevel, pLightLevel
            );
            case 3 -> positionThreeItems(
                    pPoseStack, pItemRenderer, pBuffer,
                    pStacks, pFacing, pLevel, pLightLevel
            );
        }
    }

    private static void positionTwoItems(PoseStack pPoseStack, ItemRenderer pItemRenderer, MultiBufferSource pBuffer,
                                  NonNullList<ItemStack> pStacks, Direction facing, @Nullable Level pLevel, int pLightLevel) {
        ItemStack extraDish = pStacks.get(2);
        if (!extraDish.isEmpty()) {
            renderItem(
                    pPoseStack, pItemRenderer, pBuffer,
                    extraDish, facing, pLevel, pLightLevel,
                    0.085f,
                    DinnerwareHelper::positionExtraDish, 2
            );

            ItemStack leftOverDish = !pStacks.get(0).isEmpty()
                    ? pStacks.get(0)
                    : pStacks.get(1);
            int slot = pStacks.indexOf(leftOverDish);
            renderItem(
                    pPoseStack, pItemRenderer, pBuffer,
                    leftOverDish, facing, pLevel, pLightLevel,
                    0.045f,
                    (PoseStack p) -> {}, slot
            );
        } else {
            boolean rtl = Config.rightToLeft;

            ItemStack mainDish = pStacks.get(0);
            renderItem(
                    pPoseStack, pItemRenderer, pBuffer,
                    mainDish, facing, pLevel, pLightLevel,
                    0.045f,
                    (PoseStack p) -> positionMainDish(p, false, rtl), 0
            );

            ItemStack sideDish = pStacks.get(1);
            renderItem(
                    pPoseStack, pItemRenderer, pBuffer,
                    sideDish, facing, pLevel, pLightLevel,
                    0.07f,
                    (PoseStack p) -> positionSideDish(p, false, rtl), 1
            );
        }
    }

    private static void positionThreeItems(PoseStack pPoseStack, ItemRenderer pItemRenderer, MultiBufferSource pBuffer,
                                    NonNullList<ItemStack> pStacks, Direction facing, @Nullable Level pLevel, int pLightLevel) {

        boolean rtl = Config.rightToLeft;

        for (int pSlot = 0; pSlot < pStacks.size(); pSlot++) {
            float yLevel = switch (pSlot) {
                case 1 -> 0.07f;
                case 2 -> 0.085f;
                default -> 0.045f;
            };

            Consumer<PoseStack> precisePositioner = switch (pSlot) {
                case 0 -> (PoseStack p) -> positionMainDish(p, true, rtl);
                case 1 -> (PoseStack p) -> positionSideDish(p, true, rtl);
                case 2 -> DinnerwareHelper::positionExtraDish;
                default -> (PoseStack p) -> {};
            };

            renderItem(
                    pPoseStack, pItemRenderer, pBuffer,
                    pStacks.get(pSlot), facing, pLevel, pLightLevel,
                    yLevel,
                    precisePositioner, pSlot
            );
        }
    }

    private static void renderItem(PoseStack pPoseStack, ItemRenderer pItemRenderer, MultiBufferSource pBuffer,
                            ItemStack pStack, Direction facing, @Nullable Level pLevel, int pLightLevel,
                            float pY, Consumer<PoseStack> precisePositioner,
                            int pSeed) {
        pPoseStack.pushPose();

        // Move to the center
        pPoseStack.translate(0.5f, pY, 0.5f);

        // Rotate based on facing
        switch (facing) {
            case NORTH -> {} // pPoseStack.mulPose(Axis.YP.rotationDegrees(0));
            case WEST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
        }

        // Lie flat
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        // Precise positioning
        precisePositioner.accept(pPoseStack);

        // Scale down
        pPoseStack.scale(0.4f, 0.4f, 0.4f);

        pItemRenderer.renderStatic(pStack,
                ItemDisplayContext.FIXED, pLightLevel, OverlayTexture.NO_OVERLAY,
                pPoseStack, pBuffer, pLevel, pSeed);
        pPoseStack.popPose();
    }

    private static void positionSideDish(PoseStack pPoseStack, boolean lower, boolean mirrored) {
        // Move to plate edge
        pPoseStack.translate(
                mirrored ? 0.15f : -0.15f,
                lower ? -0.05f : 0f, -0.01f);

        // Rotate
        pPoseStack.mulPose(
                mirrored
                        ? Axis.ZP.rotationDegrees(300)
                        : Axis.ZN.rotationDegrees(30)
        );

        // Tilt against plate edge
        if (mirrored) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(15));
            pPoseStack.mulPose(Axis.XN.rotationDegrees(15));
        } else {
            pPoseStack.mulPose(Axis.YN.rotationDegrees(15));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(15));
        }
    }

    private static void positionMainDish(PoseStack pPoseStack, boolean lower, boolean mirrored) {
        // Move to plate edge
        pPoseStack.translate(
                mirrored ? -0.075f : 0.05f,
                lower ? -0.05f : 0f, -0.01f);

        // Rotate
        if (!mirrored)
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));

        // Tilt against plate edge
        pPoseStack.mulPose(Axis.YN.rotationDegrees(10));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(10));
    }

    private static void positionExtraDish(PoseStack pPoseStack) {
        // Move to plate edge
        pPoseStack.translate(0f, 0.2f, -0.01f);

        // Rotate
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(40));

        // Tilt against plate edge
        pPoseStack.mulPose(Axis.YP.rotationDegrees(20));
        pPoseStack.mulPose(Axis.XN.rotationDegrees(20));
    }

    public static int getLightLevel(@NotNull Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    // ===============================================================
}
