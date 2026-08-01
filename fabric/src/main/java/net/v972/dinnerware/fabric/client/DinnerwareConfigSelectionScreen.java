package net.v972.dinnerware.fabric.client;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.v972.dinnerware.fabric.config.DinnerwareFabricConfigs;
import net.v972.dinnerware.fabric.config.FabricClientConfig;
import net.v972.dinnerware.fabric.config.FabricCommonConfig;
import org.jetbrains.annotations.NotNull;

public final class DinnerwareConfigSelectionScreen extends Screen {
    private final Screen parent;

    private ConfigType configToReload;

    private enum ConfigType {
        COMMON,
        CLIENT
    }

    public DinnerwareConfigSelectionScreen(Screen parent) {
        super(Component.translatable("config.dinnerware.selector.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        if (configToReload != null) {
            if (configToReload == ConfigType.COMMON) {
                DinnerwareFabricConfigs.reloadCommon();
            } else {
                DinnerwareFabricConfigs.reloadClient();
            }

            configToReload = null;
        }

        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = (this.width - buttonWidth) / 2;
        int firstY = this.height / 2 - 24;

        addRenderableWidget(
            Button.builder(
                Component.translatable("config.dinnerware.selector.common"),
                button -> {
                    configToReload = ConfigType.COMMON;
                    clearWidgets();

                    minecraft.setScreen(
                        AutoConfig
                            .getConfigScreen(FabricCommonConfig.class, this)
                            .get()
                    );
                }
            ).bounds(x, firstY, buttonWidth, buttonHeight).build()
        );

        addRenderableWidget(
            Button.builder(
                Component.translatable("config.dinnerware.selector.client"),
                button -> {
                    configToReload = ConfigType.CLIENT;
                    clearWidgets();

                    minecraft.setScreen(
                        AutoConfig
                            .getConfigScreen(FabricClientConfig.class, this)
                            .get()
                    );
                }
            ).bounds(
                x, firstY + 24,
                buttonWidth, buttonHeight
            ).build()
        );

        addRenderableWidget(
            Button.builder(
                Component.translatable("gui.done"),
                button -> onClose()
            ).bounds(
                x, firstY + 56,
                buttonWidth, buttonHeight
            ).build()
        );
    }

    @Override
    public void onClose() {
        if (minecraft != null) {
            minecraft.setScreen(parent);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        graphics.drawCenteredString(font, title, width / 2, 40, 0xFFFFFF);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public @NotNull Component getNarrationMessage() {
        return getTitle();
    }
}