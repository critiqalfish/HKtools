package com.smallpufferfish.hktools.features;

import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.render.RenderUtils;
import com.smallpufferfish.hktools.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

public class PestESP extends Feature {
    private static final AxisAlignedBB gardenBarnAABB = new AxisAlignedBB(new BlockPos(-46, 65, -46), new BlockPos(46, 90, 46));

    public PestESP() {
        super("PestESP");
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!activated) return;
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
        if (!HKtools.DEBUG) {
            if (!Utils.isInGarden()) return;
        }
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() == null || !Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().contains("Vacuum")) return;

        for (Object ent : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (ent instanceof EntityArmorStand) {
                EntityArmorStand as = (EntityArmorStand) ent;
                if (as.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && as.getEquipmentInSlot(4) != null && as.getEquipmentInSlot(4).getItem() == Items.skull) {
                    AxisAlignedBB aabb = as.getEntityBoundingBox();
                    if (!gardenBarnAABB.intersectsWith(aabb)) {
                        aabb = aabb.contract(0, 0.75, 0).offset(0, 0.75, 0).expand(0.1, 0.1, 0.1);
                        RenderUtils.drawAABBoutline(aabb, new Color(255, 0, 132, 255), event.partialTicks);
                        RenderUtils.drawTracer(as.getPositionEyes(event.partialTicks), new Color(255, 0, 132, 255));
                    }
                }
            }
        }
    }
}
