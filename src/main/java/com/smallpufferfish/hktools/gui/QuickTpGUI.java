package com.smallpufferfish.hktools.gui;

import com.smallpufferfish.hktools.keybinds.QuickTpKeybind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickTpGUI extends GuiScreen {
    public static boolean isOpen = false;
    private int mouseX;
    private int mouseY;
    private final ArrayList<Point> points = new ArrayList<Point>();
    private static final String[] names = new String[]{"Private\nIsland", "Skyblock\nHub", "Dungeon\nHub", "The\nBarn", "Mushroom\nDesert", "The\nPark", "Gold\nMine", "Deep\nCaverns", "Dwarven\nMines", "Crystal\nHollows", "Spider's\nDen", "The\nEnd", "Crimson\nIsle", "The\nGarden", "\nJerry's\nWorkshop"};
    private static final String[] warps = new String[]{"/is", "/hub", "/warp dungeons", "/warp barn", "/warp desert", "/warp park", "/warp gold", "/warp deep", "/warp dwarves", "/warp crystals", "/warp spider", "/warp end", "/warp crimson", "/warp garden", "/savethejerrys"};

    @SideOnly(Side.CLIENT)
    @Override
    public void initGui() {
        int sectors = names.length;
        double sectorAngle = 360 / sectors;

        for (int i = 0; i < sectors; i++) {
            double placeAngle = Math.toRadians(i * sectorAngle);

            int x = (int) (width / 2 + 200 * Math.sin(placeAngle));
            int y = (int) (height / 2 - 200 * Math.cos(placeAngle));

            points.add(new Point(x, y));
        }
        super.initGui();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!Keyboard.isKeyDown(QuickTpKeybind.keybind.getKeyCode())) {
            isOpen = false;
            mc.displayGuiScreen(null);
        }

        mc.mouseHelper.grabMouseCursor();

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        int onSlice = getMouseSlice(new Point(mouseX, mouseY), new Point(width / 2, height / 2), names.length);

        for (int i = 0; i < points.size(); i++) {
            int color = new Color(255, 255, 255, 255).getRGB();
            if (i == onSlice) {
                color = new Color(255, 0, 255, 255).getRGB();
            }

            String[] lines = names[i].split("\n");
            drawCenteredString(fontRendererObj, lines[0], points.get(i).x, points.get(i).y - 5, color);
            drawCenteredString(fontRendererObj, lines[1], points.get(i).x, points.get(i).y + 5, color);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleMouseInput() throws IOException {
        int mouseX = Mouse.getEventX() * width / mc.displayWidth;
        int mouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1;

        int centerX = width / 2;
        int centerY = height / 2;
        double distance = Math.sqrt(Math.pow(Math.abs(mouseX - centerX), 2) + Math.pow(Math.abs(mouseY - centerY), 2));

        if (distance > 20) {
            double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
            mouseX = (int) (centerX + 19 * Math.cos(angle));
            mouseY = (int) (centerY + 19 * Math.sin(angle) * -1);
            Mouse.setCursorPosition(mouseX * 2, mouseY * 2);
        }

        super.handleMouseInput();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onGuiClosed() {
        int onSlice = getMouseSlice(new Point(mouseX, mouseY), new Point(width / 2, height / 2), names.length);
        if (onSlice != -1) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(warps[onSlice]);
        }
        super.onGuiClosed();
    }

    private static int getMouseSlice(Point mousePos, Point centerPos, int sectorCount) {
        double sectorAngle = 360.0 / sectorCount;

        double mouseAngle = Math.toDegrees(Math.atan2(centerPos.y - mousePos.y, centerPos.x - mousePos.x)) - 90;
        if (mouseAngle < 0) {
            mouseAngle += 360;
        }

        for (int i = 0; i < sectorCount; i++) {
            double startAngle = i * sectorAngle - sectorAngle / 2;
            double endAngle = (i + 1) * sectorAngle - sectorAngle / 2;

            if (mouseAngle >= startAngle && mouseAngle < endAngle) {
                return i;
            }
        }
        return -1;
    }
}
