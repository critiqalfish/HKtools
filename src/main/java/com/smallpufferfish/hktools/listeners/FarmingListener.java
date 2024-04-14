package com.smallpufferfish.hktools.listeners;

import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.features.Farming;
import com.smallpufferfish.hktools.keybinds.FarmingKeybinds;
import com.smallpufferfish.hktools.utils.CropMode;
import com.smallpufferfish.hktools.utils.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FarmingListener {
    public static boolean activated = HKtools.strToBool(HKtools.CONFIG.getProperty("Farming"));
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!activated) return;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;

        if (FarmingKeybinds.homeKeybind.isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp garden");
            return;
        }

        if (!HKtools.DEBUG) {
            if (!Utils.isInGarden()) return;
        }

        if (Farming.crop == null) return;
        switch (Farming.crop) {
            case MELON:
                Farming.melonFarmer();
            case PUMPKIN:
                Farming.pumpkinFarmer();
                break;
            case CACTUS:
                Farming.cactusFarmer();
                break;
            case WHEAT:
                Farming.wheatFarmer();
                break;
            case CARROT:
                Farming.carrotFarmer();
                break;
            case POTATO:
                Farming.potatoFarmer();
                break;
            default:
                break;
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!activated) return;

        if (!HKtools.DEBUG) {
            if (!Utils.isInGarden()) return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        MovingObjectPosition target = mc.objectMouseOver;

        if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (mc.theWorld == null) return;
            IBlockState blockState = mc.theWorld.getBlockState(target.getBlockPos());
            try {
                CropMode newMode = CropMode.valueOf(blockState.getBlock().getUnlocalizedName().substring("tile.".length()).toUpperCase());
                if (newMode != Farming.crop) {
                    Farming.crop = newMode;
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("HKtools: selected crop " + Farming.crop));
                }
            } catch (IllegalArgumentException ignored) {}
        }

        if (mc.gameSettings.mouseSensitivity != -1F/3F) {
            Farming.sensitivity = mc.gameSettings.mouseSensitivity;
        }

        if (Farming.goingRight || Farming.goingLeft || Farming.goingForward) {
            mc.gameSettings.mouseSensitivity = -1F/3F;
        } else {
            mc.gameSettings.mouseSensitivity = Farming.sensitivity;
        }
    }
}
