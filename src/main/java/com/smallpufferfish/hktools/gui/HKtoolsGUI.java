package com.smallpufferfish.hktools.gui;

import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.features.*;
import com.smallpufferfish.hktools.keybinds.HKtoolsKeybind;
import com.smallpufferfish.hktools.keybinds.QuickTpKeybind;
import com.smallpufferfish.hktools.listeners.FarmingListener;
import com.smallpufferfish.hktools.listeners.QuickTpListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class HKtoolsGUI extends GuiScreen {
    private final long timeOpened = System.currentTimeMillis();
    private final double boxesStartW = 0.12;
    private final double boxesStartH = 0.20;
    private final double boxesHmod = 0.05;

    @SideOnly(Side.CLIENT)
    @Override
    public void initGui() {
        buttonList.clear();

        for (Feature f : HKtools.features) {
            buttonList.add(new GuiCheckBox(f.id, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * f.id)), f.name, f.activated));
        }

        super.initGui();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        long timeSinceOpen = System.currentTimeMillis() - timeOpened;
        int marginH = (int) (width * 0.1);
        int marginV = (int) (height * 0.1);

        int alphaBG = 204;
        int alpha = 255;
        if (timeSinceOpen < 1) {
            alphaBG = 0;
            alpha = 0;
        }
        else if (timeSinceOpen < 500) {
            alphaBG = (int) (204 * timeSinceOpen / 500);
            alpha = (int) (255 * timeSinceOpen / 500);
        }

        drawRect(marginH, marginV, width - marginH, height - marginV, new Color(0, 0, 0, alphaBG).getRGB());

        GlStateManager.pushMatrix();
        GlStateManager.scale(3, 3, 1);
        drawCenteredString(fontRendererObj, "HKtools", (int) (width * 0.5 / 3), (int) (height * 0.12 / 3), new Color(255, 0, 132, alpha).getRGB());
        GlStateManager.popMatrix();

        drawHorizontalLine((int) (width * 0.12), (int) (width - width * 0.12), (int) (height * 0.18), new Color(255, 0, 132, alpha).getRGB());

        GlStateManager.pushMatrix();
        for (GuiButton button : buttonList) {
            button.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }
        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        for (Feature f : HKtools.features) {
            if (button.id == f.id) f.toggle();
        }
        super.actionPerformed(button);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == HKtoolsKeybind.keybind.getKeyCode()) {
            mc.displayGuiScreen(null);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
