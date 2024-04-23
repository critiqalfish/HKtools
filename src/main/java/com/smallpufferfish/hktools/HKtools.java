package com.smallpufferfish.hktools;

import com.smallpufferfish.hktools.commands.HKtoolsCommand;
import com.smallpufferfish.hktools.features.*;
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
import java.util.ArrayList;
import java.util.List;
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
    public static final String VERSION = "0.2.2";

    public static final boolean DEBUG = true;
    public static final Logger LOGGER = Logger.getLogger("HKTools");
    public static FileHandler logFH;
    public static Properties CONFIG;

    public static ArrayList<Feature> features = new ArrayList<>();
    
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

            logFH = new FileHandler("HKtools/hktools.log", true);
            LOGGER.addHandler(logFH);
            SimpleFormatter formatter = new SimpleFormatter();
            logFH.setFormatter(formatter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        MinecraftForge.EVENT_BUS.register(this);

        features.add(new FarmingListener());
        features.add(new ContinuousHit());
        features.add(new QuickTpListener());
        features.add(new PestESP());
        features.add(new F7TermWaypoints());
        features.add(new HoldClick());
        features.add(new LonelyMode());
        for (Feature f : features) {
            MinecraftForge.EVENT_BUS.register(f);
        }

        registerKeybinds();
        registerCommands();

        try {
            CONFIG.store(new FileWriter("HKtools/hktools.config"), null);
        } catch (Exception ex) {
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
        HoldClickKeybind.register();
        QuickTpKeybind.register();
    }

    private void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new HKtoolsCommand());
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
