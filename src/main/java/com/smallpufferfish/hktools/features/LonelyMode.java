package com.smallpufferfish.hktools.features;

import com.smallpufferfish.hktools.HKtools;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LonelyMode {
    public static boolean activated = HKtools.strToBool(HKtools.CONFIG.getProperty("LonelyMode"));
    private static final Pattern chatUserMessage = Pattern.compile("(?:\\[\\d{1,3}\\])\\s?(?:\\[.+\\])?\\s?(.+):\\s(.*)");

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (event.entityPlayer.getName().contains(Minecraft.getMinecraft().getSession().getUsername())) return;
        if (activated) event.setCanceled(true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (!activated) return;

        Matcher match = chatUserMessage.matcher(event.message.getUnformattedText());
        if (match.matches()) {
            HKtools.LOGGER.info(match.group(1));
            HKtools.LOGGER.info(match.group(2));
            if (match.group(1).contains(Minecraft.getMinecraft().getSession().getUsername())) {

            }
            else if (match.group(2).contains(Minecraft.getMinecraft().getSession().getUsername())) {

            }
            else {
                event.setCanceled(true);
            }
        }
    }
}
