package com.smallpufferfish.hktools.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class ContinuousHitKeybind {
    public static KeyBinding keybind;

    public static void register() {
        keybind = new KeyBinding("toggle continuous hit", Keyboard.KEY_RCONTROL, "HKtools");
        ClientRegistry.registerKeyBinding(keybind);
    }
}
