package com.smallpufferfish.hktools.utils;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DelayedTask {
    private int counter;
    private Runnable runnable;

    public DelayedTask(Runnable run, int ticks){
        counter = ticks;
        this.runnable = run;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if (event.phase != TickEvent.Phase.START) return;

        if(counter <= 0){
            MinecraftForge.EVENT_BUS.unregister(this);
            runnable.run();
        }

        counter--;
    }
}


