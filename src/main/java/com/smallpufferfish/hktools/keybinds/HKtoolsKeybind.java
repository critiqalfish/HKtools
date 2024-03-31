package com.smallpufferfish.hktools.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class HKtoolsKeybind {
    public static KeyBinding keybind;

    public static void register() {
        keybind = new KeyBinding("open gui", Keyboard.KEY_RMENU, "HKtools");
        ClientRegistry.registerKeyBinding(keybind);
    }
}
