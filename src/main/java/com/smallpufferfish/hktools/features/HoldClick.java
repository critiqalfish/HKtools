package com.smallpufferfish.hktools.features;

import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.keybinds.HoldClickKeybind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HoldClick extends Feature {
    public HoldClick() {
        super("Hold Click");
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!activated) return;

        Minecraft mc = Minecraft.getMinecraft();
        MovingObjectPosition target = mc.objectMouseOver;

        if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (mc.theWorld == null) return;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (HoldClickKeybind.keybind.isPressed()) {
            if (!activated) {
                activated = true;
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("HKtools: activated hold click"));
            }
            else {
                activated = false;
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("HKtools: deactivated hold click"));
            }
        }
    }
}
