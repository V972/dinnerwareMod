package net.v972.dinnerware.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.v972.dinnerware.Config;
import net.v972.dinnerware.DinnerwareMod;
import net.v972.dinnerware.block.entity.PlateBlockBlockEntity;
import net.v972.dinnerware.item.custom.TrayItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TrayItemHud implements IGuiOverlay {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            DinnerwareMod.MOD_ID, "textures/gui/tray_hud.png");

    //deadlock prevention
    private static class Holder {
        //this can't never ever be null
        private static final TrayItemHud INSTANCE = makeInstance();
    }

    public static TrayItemHud getInstance() {
        return Holder.INSTANCE;
    }

    public static TrayItemHud makeInstance() {
        return new TrayItemHud(Minecraft.getInstance());
    }

    protected final Minecraft mc;
    //behold states
    @Nullable
    private TrayItem itemUsed;
    //private SlotReference stackSlot;

    protected TrayItemHud(Minecraft minecraft) {
        this.mc = minecraft;
    }


    public boolean isActive() {
        return itemUsed != null;
    }


//    public void setUsingItem(SlotReference slot, LivingEntity player) {
//        stackSlot = slot;
//        if (slot.getItem(player) instanceof TrayItem trayItem) {
//            itemUsed = trayItem;
//        } else {
//            itemUsed = null;
//        }
//    }

    private void closeHud() {
        itemUsed = null;
        //stackSlot = SlotReference.EMPTY;
    }

    //@EventCalled
    public boolean onMouseScrolled(double scrollDelta) {
        if (itemUsed != null) {
            int amount = scrollDelta > 0 ? -1 : 1;
            sendCycle(amount);
            return true;
        }
        return false;
    }

    private void sendCycle(int slotsMoved) {
//        var data = getItemUsedData();
//        if (data != null) {
//            ModNetwork.CHANNEL.sendToServer(new ServerBoundCycleSelectableContainerItemPacket(slotsMoved, stackSlot));
//            //update client immediately. stacks now may be desynced
//            data.cycle(slotsMoved);
//        }
    }

//    private SelectableContainerItem.AbstractData getItemUsedData() {
//        if (itemUsed == null) return null;
//        ItemStack stack = stackSlot.get(Minecraft.getInstance().player);
//        if (!stack.is(itemUsed)) return null;
//        return itemUsed.getData(stack);
//    }

    public void render(GuiGraphics graphics, float partialTicks) {
        var w = this.mc.getWindow();
        this.render(graphics, partialTicks, w.getGuiScaledWidth(), w.getGuiScaledHeight());
    }

    public void render(GuiGraphics graphics, float partialTicks, int screenWidth, int screenHeight) {
        if (itemUsed == null) return;
        if (!(mc.getCameraEntity() instanceof Player)) {
            closeHud();
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) {
            closeHud();
            return;
        }

//        var data = getItemUsedData();
//        if (data == null) {
//            closeHud();
//            return;
//        }
        ///gui.setupOverlayRenderState(true, false);
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();

        List<ItemStack> items = List.of(); //data.getContentView();
        int slots = items.size();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int centerX = screenWidth / 2;

        poseStack.pushPose();
        poseStack.translate(0, 0, -90);

        int uWidth = 170;
        int px = uWidth / 2;
        int py = screenHeight / 2 - 40;

        //px += Config.trayGuiX;
        //py += Config.trayGuiY;

        //graphics.blit(TEXTURE, centerX - px, py, 0, 0, uWidth - 1, 40);
        //graphics.blit(TEXTURE, centerX + px - 1, py, 0, 0, 1, 40);

        poseStack.popPose();

        int i1 = 1;

        for (int i = 0; i < slots; ++i) {
            int kx = centerX - px + 3 + i * 20;
            renderSlot(graphics, kx, py + 3, items.get(i), i1++, mc.font);
        }
        RenderSystem.disableBlend();
        ItemStack topStack = items.get(0);
        if (!topStack.isEmpty()) {
            drawHighlight(graphics, screenWidth, py, topStack);
        }

        poseStack.popPose();
    }

    public void drawHighlight(GuiGraphics graphics, int screenWidth, int py, ItemStack selectedStack) {
        int l;

        MutableComponent mutablecomponent = Component.empty().append(selectedStack.getHoverName()).withStyle(selectedStack.getRarity().getStyleModifier());
        if (selectedStack.hasCustomHoverName()) {
            mutablecomponent.withStyle(ChatFormatting.ITALIC);
        }

        // append "with Food"
        boolean withFood = false;
        CompoundTag blockEntityData = BlockItem.getBlockEntityData(selectedStack);
        if (blockEntityData != null && blockEntityData.contains(PlateBlockBlockEntity.ITEMS_TAG)) {
            blockEntityData = blockEntityData.getCompound(PlateBlockBlockEntity.ITEMS_TAG);
            if (blockEntityData.contains("Items", Tag.TAG_LIST)) {
                ListTag plateListTag = blockEntityData.getList("Items", 10);
                withFood = !plateListTag.isEmpty();
            }
        }

        if (withFood) mutablecomponent =
                Component.translatable("container.dinnerware.tray.with_food", mutablecomponent);

        Component highlightTip = selectedStack.getHighlightTip(mutablecomponent);
        int fontWidth = mc.font.width(highlightTip);
        int nx = (screenWidth - fontWidth) / 2;
        int ny = py - 19;

        l = 255;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.fill(nx - 2, ny - 2, nx + fontWidth + 2, ny + 9 + 2, mc.options.getBackgroundColor(0));
        Font font = IClientItemExtensions.of(selectedStack).getFont(selectedStack, IClientItemExtensions.FontContext.SELECTED_ITEM_NAME);
        if (font == null) {
            graphics.drawString(mc.font, highlightTip, nx, ny, 0xFFFFFF + (l << 24));
        } else {
            nx = (screenWidth - font.width(highlightTip)) / 2;
            graphics.drawString(font, highlightTip, nx, ny, 0xFFFFFF + (l << 24));
        }
        RenderSystem.disableBlend();
    }

    private void renderSlot(GuiGraphics graphics, int pX, int pY, ItemStack pStack, int seed, Font font) {
        if (!pStack.isEmpty()) {
            graphics.renderItem(pStack, pX, pY, seed);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            graphics.renderItemDecorations(font, pStack, pX, pY);
        }
    }


    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        this.render(guiGraphics, partialTick, screenWidth, screenHeight);
    }
}
