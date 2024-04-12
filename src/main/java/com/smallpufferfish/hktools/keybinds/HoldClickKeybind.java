package com.smallpufferfish.hktools.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class HoldClickKeybind {
    public static KeyBinding keybind;

    public static void register() {
        keybind = new KeyBinding("toggle hold click", Keyboard.KEY_RCONTROL, "HKtools");
        ClientRegistry.registerKeyBinding(keybind);
    }
}
