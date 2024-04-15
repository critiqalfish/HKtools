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
    private GuiCheckBox farmingRebinder;
    private GuiCheckBox pestESP;
    private GuiCheckBox quickTP;
    private GuiCheckBox f7TermWPs;
    private GuiCheckBox contHit;
    private GuiCheckBox holdClick;
    private GuiCheckBox lonelyMode;
    private final double boxesStartW = 0.12;
    private final double boxesStartH = 0.20;
    private final double boxesHmod = 0.05;

    @SideOnly(Side.CLIENT)
    @Override
    public void initGui() {
        buttonList.clear();
        farmingRebinder = new GuiCheckBox(0, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * 0)), "Farming Rebinder", FarmingListener.activated);
        pestESP = new GuiCheckBox(1, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * 1)), "Pest ESP", PestESP.activated);
        quickTP = new GuiCheckBox(2, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * 2)), "QuickTP Menu", QuickTpListener.activated);
        f7TermWPs = new GuiCheckBox(3, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * 3)), "F7 Terminal Waypoints", F7TermWaypoints.activated);
        contHit = new GuiCheckBox(4, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * 4)), "Continuous Hit", ContinuousHit.activated);
        holdClick = new GuiCheckBox(5, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * 5)), "Hold Click", HoldClick.activated);
        lonelyMode = new GuiCheckBox(6, (int) (width * boxesStartW), (int) (height * (boxesStartH + boxesHmod * 6)), "Lonely Mode", LonelyMode.activated);
        buttonList.add(farmingRebinder);
        buttonList.add(pestESP);
        buttonList.add(quickTP);
        buttonList.add(f7TermWPs);
        buttonList.add(contHit);
        buttonList.add(holdClick);
        buttonList.add(lonelyMode);
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
        switch (button.id) {
            case 0:
                FarmingListener.activated = !FarmingListener.activated;
                HKtools.CONFIG.setProperty("Farming", HKtools.boolToStr(FarmingListener.activated));
                break;
            case 1:
                PestESP.activated = !PestESP.activated;
                HKtools.CONFIG.setProperty("PestESP", HKtools.boolToStr(PestESP.activated));
                break;
            case 2:
                QuickTpListener.activated = !QuickTpListener.activated;
                HKtools.CONFIG.setProperty("QuickTpMenu", HKtools.boolToStr(QuickTpListener.activated));
                break;
            case 3:
                F7TermWaypoints.activated = !F7TermWaypoints.activated;
                HKtools.CONFIG.setProperty("F7TermWaypoints", HKtools.boolToStr(F7TermWaypoints.activated));
                break;
            case 4:
                ContinuousHit.activated = !ContinuousHit.activated;
                HKtools.CONFIG.setProperty("ContinuousHit", HKtools.boolToStr(ContinuousHit.activated));
                break;
            case 5:
                HoldClick.activated = !HoldClick.activated;
                HKtools.CONFIG.setProperty("HoldClick", HKtools.boolToStr(HoldClick.activated));
                break;
            case 6:
                LonelyMode.activated = !LonelyMode.activated;
                HKtools.CONFIG.setProperty("LonelyMode", HKtools.boolToStr(LonelyMode.activated));
                break;
        }
        HKtools.CONFIG.store(new FileWriter("HKtools/hktools.config"), null);
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
