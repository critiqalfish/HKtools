package com.smallpufferfish.hktools.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class FarmingKeybinds {
    public static KeyBinding rightKeybind;
    public static KeyBinding leftKeybind;
    public static KeyBinding miscKeybind;
    public static KeyBinding homeKeybind;

    public static void register() {
        rightKeybind = new KeyBinding("right", Keyboard.KEY_RIGHT, "HKtools");
        leftKeybind = new KeyBinding("left", Keyboard.KEY_LEFT, "HKtools");
        miscKeybind = new KeyBinding("misc", Keyboard.KEY_UP, "HKtools");
        homeKeybind = new KeyBinding("home", Keyboard.KEY_DOWN, "HKtools");
        ClientRegistry.registerKeyBinding(rightKeybind);
        ClientRegistry.registerKeyBinding(leftKeybind);
        ClientRegistry.registerKeyBinding(miscKeybind);
        ClientRegistry.registerKeyBinding(homeKeybind);
    }
}
