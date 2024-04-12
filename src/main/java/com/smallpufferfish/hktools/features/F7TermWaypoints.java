package com.smallpufferfish.hktools.features;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
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
    public int phase = -1;

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
        for (JsonElement term : terms) {
            JsonArray locs = term.getAsJsonObject().getAsJsonArray("location");
            BlockPos pos = new BlockPos(locs.get(0).getAsInt(), locs.get(1).getAsInt(), locs.get(1).getAsInt());
            AxisAlignedBB box = new AxisAlignedBB(pos, pos.add(1, 1, 1));
            Color color = new Color(255, 0, 132, 100 );
            RenderUtils.drawAABBfilled(box, color, event.partialTicks);
        }
    }
}
