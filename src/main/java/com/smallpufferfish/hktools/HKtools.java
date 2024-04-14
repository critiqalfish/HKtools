package com.smallpufferfish.hktools;

import com.smallpufferfish.hktools.commands.HKtoolsCommand;
import com.smallpufferfish.hktools.features.ContinuousHit;
import com.smallpufferfish.hktools.features.F7TermWaypoints;
import com.smallpufferfish.hktools.features.HoldClick;
import com.smallpufferfish.hktools.features.PestESP;
import com.smallpufferfish.hktools.gui.HKtoolsGUI;
import com.smallpufferfish.hktools.keybinds.*;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Mod(   modid = HKtools.MODID,
        name = HKtools.MODID,
        clientSideOnly = true,
        version = HKtools.VERSION)
public class HKtools {
    public static final String MODID = "HKtools";
    public static final String VERSION = "0.2.1";

    public static final boolean DEBUG = true;
    public static final Logger LOGGER = Logger.getLogger("HKTools");
    public static FileHandler logFH;
    public static Properties CONFIG;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            if (!Files.isDirectory(Paths.get("HKtools"))) {
                Files.createDirectory(Paths.get("HKtools"));
            }

            File cfg = new File("HKtools/hktools.config");
            cfg.createNewFile();
            CONFIG = new Properties();
            CONFIG.load(Files.newInputStream(Paths.get("HKtools/hktools.config")));
            configureConfig();

            logFH = new FileHandler("HKtools/hktools.log", true);
            LOGGER.addHandler(logFH);
            SimpleFormatter formatter = new SimpleFormatter();
            logFH.setFormatter(formatter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new FarmingListener());
        MinecraftForge.EVENT_BUS.register(new ContinuousHit());
        MinecraftForge.EVENT_BUS.register(new HoldClick());
        MinecraftForge.EVENT_BUS.register(new QuickTpListener());
        MinecraftForge.EVENT_BUS.register(new PestESP());
        MinecraftForge.EVENT_BUS.register(new F7TermWaypoints());
        registerKeybinds();
        registerCommands();

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
        HoldClickKeybind.register();
        QuickTpKeybind.register();
    }

    private void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new HKtoolsCommand());
    }

    private void configureConfig() throws IOException {
        if (CONFIG.getProperty("Farming") == null) {
            CONFIG.setProperty("Farming", "enabled");
        }
        if (CONFIG.getProperty("PestESP") == null) {
            CONFIG.setProperty("PestESP", "enabled");
        }
        if (CONFIG.getProperty("QuickTpMenu") == null) {
            CONFIG.setProperty("QuickTpMenu", "enabled");
        }
        if (CONFIG.getProperty("F7TermWaypoints") == null) {
            CONFIG.setProperty("F7TermWaypoints", "enabled");
        }
        if (CONFIG.getProperty("ContinuousHit") == null) {
            CONFIG.setProperty("ContinuousHit", "disabled");
        }
        if (CONFIG.getProperty("HoldClick") == null) {
            CONFIG.setProperty("HoldClick", "disabled");
        }

        CONFIG.store(new FileWriter("HKtools/hktools.config"), null);
    }

    public static boolean strToBool(String s) {
        if (Objects.equals(s, "enabled")) return true;
        else if (Objects.equals(s, "disabled")) return false;
        return false;
    }

    public static String boolToStr(boolean b) {
        if (b) return "enabled";
        else if (!b) return "disabled";
        return "";
    }
}
