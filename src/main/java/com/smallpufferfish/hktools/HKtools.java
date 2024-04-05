package com.smallpufferfish.hktools;

import com.smallpufferfish.hktools.commands.HKtoolsCommand;
import com.smallpufferfish.hktools.features.ContinuousHit;
import com.smallpufferfish.hktools.gui.HKtoolsGUI;
import com.smallpufferfish.hktools.gui.QuickTpGUI;
import com.smallpufferfish.hktools.keybinds.ContinuousHitKeybind;
import com.smallpufferfish.hktools.keybinds.FarmingKeybinds;
import com.smallpufferfish.hktools.keybinds.HKtoolsKeybind;
import com.smallpufferfish.hktools.keybinds.QuickTpKeybind;
import com.smallpufferfish.hktools.listeners.FarmingListener;
import com.smallpufferfish.hktools.listeners.QuickTpListener;
import com.smallpufferfish.hktools.utils.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Mod(   modid = HKtools.MODID,
        name = HKtools.MODID,
        clientSideOnly = true,
        version = HKtools.VERSION)
public class HKtools {
    public static final String MODID = "HKtools";
    public static final String VERSION = "0.2";

    public static final boolean DEBUG = false;
    public static final Logger LOGGER = Logger.getLogger("HKTools");
    public static FileHandler logFH;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new FarmingListener());
        MinecraftForge.EVENT_BUS.register(new ContinuousHit());
        MinecraftForge.EVENT_BUS.register(new QuickTpListener());
        registerKeybinds();
        registerCommands();
        try {
            logFH = new FileHandler("hktools.log");
            LOGGER.addHandler(logFH);
            SimpleFormatter formatter = new SimpleFormatter();
            logFH.setFormatter(formatter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("--- HKtools by smallpufferfish was loaded! ---");
    }

    @Mod.EventHandler
    public void shutdown(FMLServerStoppingEvent event) {
        logFH.close();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (HKtoolsKeybind.keybind.isPressed()) {
            new DelayedTask((() -> Minecraft.getMinecraft().displayGuiScreen(new HKtoolsGUI())), 1);
        }
    }

    private void registerKeybinds() {
        HKtoolsKeybind.register();
        FarmingKeybinds.register();
        ContinuousHitKeybind.register();
        QuickTpKeybind.register();
    }

    private void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new HKtoolsCommand());
    }
}
