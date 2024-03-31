package com.smallpufferfish.hktools.features;

import com.smallpufferfish.hktools.keybinds.ContinuousHitKeybind;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContinuousHit {
    public static boolean activated = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!activated) return;

        Minecraft mc = Minecraft.getMinecraft();
        MovingObjectPosition target = mc.objectMouseOver;

        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            if (mc.thePlayer != null && mc.thePlayer.isSwingInProgress == false) {
                if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                    mc.thePlayer.swingItem();
                    mc.playerController.attackEntity(mc.thePlayer, target.entityHit);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (ContinuousHitKeybind.keybind.isPressed()) {
            if (!activated) {
                activated = true;
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("HKtools: activated continuous hit"));
            }
            else {
                activated = false;
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("HKtools: deactivated continuous hit"));
            }

        }
    }
}
