package com.smallpufferfish.hktools.features;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.render.RenderUtils;
import com.smallpufferfish.hktools.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class F7TermWaypoints {
    JsonArray terms;
    private int gate = -1;
    private int termsDone = -1;

    public F7TermWaypoints() {
        try {
            ResourceLocation loc = new ResourceLocation(HKtools.MODID, "data/NotStolenFromSoopy.json");
            InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Gson gson = new Gson();
            terms = gson.fromJson(reader, JsonElement.class).getAsJsonArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (gate == -1) return;
        if (!Utils.isInDungeons() && Utils.dungeonFloor() != 7) return;

        for (JsonElement term : terms) {
            JsonArray locs = term.getAsJsonObject().getAsJsonArray("location");
            BlockPos pos = new BlockPos(locs.get(0).getAsInt(), locs.get(1).getAsInt(), locs.get(2).getAsInt());
            AxisAlignedBB box = new AxisAlignedBB(pos, pos.add(1, 1, 1));
            Color color = new Color(255, 0, 132, 120);
            RenderUtils.drawAABBfilled(box, color, event.partialTicks);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type > 1) return;
        if (!Utils.isInDungeons() && Utils.dungeonFloor() != 7) return;

        if (Utils.clean(event.message.getUnformattedText()).contains("[BOSS] Goldor: Who dares trespass into my domain?")) {
            gate = 0;
            termsDone = 0;
            HKtools.LOGGER.info("goldor start");
        }
        else if (Utils.clean(event.message.getUnformattedText()).contains("activated a terminal!") ||
                Utils.clean(event.message.getUnformattedText()).contains("completed a device!") ||
                Utils.clean(event.message.getUnformattedText()).contains("activated a lever!")) {
            termsDone++;
            HKtools.LOGGER.info("term/dev/lever activated");
            if (gate == 0 && termsDone == 7) {
                gate++;
                termsDone = 0;
            }
            else if (gate == 1 && termsDone == 8) {
                gate++;
                termsDone = 0;
            }
            else if (gate == 2 && termsDone == 7) {
                gate++;
                termsDone = 0;
            }
            else if (gate == 3 && termsDone == 7) {
                gate = -1;
                termsDone = -1;
            }
        }
        else if (Utils.clean(event.message.getUnformattedText()).contains("[BOSS] Goldor: Stop touching those terminals!") || Utils.clean(event.message.getUnformattedText()).contains("[BOSS] Goldor: Now that you're a Ghost, can you help me clean up?")) {
            gate = -1;
            termsDone = -1;
            HKtools.LOGGER.info("goldor end");
        }
    }
}
