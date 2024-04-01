package com.smallpufferfish.hktools.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class QuickTpKeybind {
    public static KeyBinding keybind;

    public static void register() {
        keybind = new KeyBinding("quick tp menu", Keyboard.KEY_LMENU, "HKtools");
        ClientRegistry.registerKeyBinding(keybind);
    }
}
