package com.smallpufferfish.hktools.listeners;

import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.features.Feature;
import com.smallpufferfish.hktools.gui.QuickTpGUI;
import com.smallpufferfish.hktools.keybinds.QuickTpKeybind;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QuickTpListener extends Feature {
    public QuickTpListener() {
        super("Quick TP Menu");
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!activated) return;
        if (QuickTpKeybind.keybind.isPressed()) {
            QuickTpGUI.isOpen = true;
            Minecraft.getMinecraft().displayGuiScreen(new QuickTpGUI());
        }
    }
}
