package com.smallpufferfish.hktools.features;

import com.smallpufferfish.hktools.keybinds.FarmingKeybinds;
import com.smallpufferfish.hktools.utils.CropMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Farming {
    public static CropMode crop = CropMode.NONE;
    public static float sensitivity;
    public static boolean goingRight = false;
    public static boolean goingLeft = false;
    public static boolean goingForward = false;

    public static void melonFarmer() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

        if (FarmingKeybinds.rightKeybind.isKeyDown()) {
            if (!goingRight) {
                goingRight = true;
                doKeys(true, gameSettings.keyBindRight, gameSettings.keyBindForward, gameSettings.keyBindAttack);
            }
        } else {
            if (goingRight) {
                goingRight = false;
                doKeys(false, gameSettings.keyBindRight, gameSettings.keyBindForward, gameSettings.keyBindAttack);
            }
        }

        if (FarmingKeybinds.leftKeybind.isKeyDown()) {
            if (!goingLeft) {
                goingLeft = true;
                doKeys(true, gameSettings.keyBindLeft, gameSettings.keyBindForward, gameSettings.keyBindAttack);
            }
        } else {
            if (goingLeft) {
                goingLeft = false;
                doKeys(false, gameSettings.keyBindLeft, gameSettings.keyBindForward, gameSettings.keyBindAttack);
            }
        }
    }

    public static void pumpkinFarmer() {
        melonFarmer();
    }

    public static void cactusFarmer() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

        if (FarmingKeybinds.rightKeybind.isKeyDown()) {
            if (!goingRight) {
                goingRight = true;
                doKeys(true, gameSettings.keyBindRight, gameSettings.keyBindAttack);
            }
        } else {
            if (goingRight) {
                goingRight = false;
                doKeys(false, gameSettings.keyBindRight, gameSettings.keyBindAttack);
            }
        }

        if (FarmingKeybinds.leftKeybind.isKeyDown()) {
            if (!goingLeft) {
                goingLeft = true;
                doKeys(true, gameSettings.keyBindLeft, gameSettings.keyBindAttack);
            }
        } else {
            if (goingLeft) {
                goingLeft = false;
                doKeys(false, gameSettings.keyBindLeft, gameSettings.keyBindAttack);
            }
        }

        if (FarmingKeybinds.miscKeybind.isKeyDown()) {
            if (!goingForward) {
                goingForward = true;
                doKeys(true, gameSettings.keyBindForward);
            }
        } else {
            if (goingForward) {
                goingForward = false;
                doKeys(false, gameSettings.keyBindForward);
            }
        }
    }

    public static void wheatFarmer() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

        if (FarmingKeybinds.rightKeybind.isKeyDown()) {
            if (!goingRight) {
                goingRight = true;
                doKeys(true, gameSettings.keyBindRight, gameSettings.keyBindAttack);
            }
        } else {
            if (goingRight) {
                goingRight = false;
                doKeys(false, gameSettings.keyBindRight, gameSettings.keyBindAttack);
            }
        }

        if (FarmingKeybinds.leftKeybind.isKeyDown()) {
            if (!goingLeft) {
                goingLeft = true;
                doKeys(true, gameSettings.keyBindLeft, gameSettings.keyBindAttack);
            }
        } else {
            if (goingLeft) {
                goingLeft = false;
                doKeys(false, gameSettings.keyBindLeft, gameSettings.keyBindAttack);
            }
        }
    }

    public static void potatoFarmer() {
        wheatFarmer();
    }

    public static void carrotFarmer() {
        wheatFarmer();
    }

    private static void doKeys(boolean pressed, KeyBinding... keys) {
        for (KeyBinding key : keys) {
            KeyBinding.setKeyBindState(key.getKeyCode(), pressed);
        }
    }
}
