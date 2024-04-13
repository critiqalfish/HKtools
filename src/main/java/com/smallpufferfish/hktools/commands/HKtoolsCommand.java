package com.smallpufferfish.hktools.commands;

import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.gui.HKtoolsGUI;
import com.smallpufferfish.hktools.utils.DelayedTask;
import com.smallpufferfish.hktools.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class HKtoolsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "hktools";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    private static final String[] SUBCOMMANDS = {"debug"};

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, SUBCOMMANDS);
        }
        return Collections.emptyList();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("HKtools v" + HKtools.VERSION));
            new DelayedTask((() -> Minecraft.getMinecraft().displayGuiScreen(new HKtoolsGUI())), 1);
        }
        else if (args[0].equals("debug") && args.length == 2) {
            switch (args[1]) {
                case "sb":
                    HKtools.LOGGER.info("yo");
                    List<String> sb = Utils.getScoreboardLines();
                    for (String line : sb) {
                        HKtools.LOGGER.info(line);
                    }
                    break;
                case "map":
                    ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
                    if (stack.getItem() == Items.filled_map) {
                        HKtools.LOGGER.info(Arrays.deepToString(Utils.getMap(stack)));
                    }
                    break;
                case "item":
                    ItemStack item = Minecraft.getMinecraft().thePlayer.getHeldItem();
                    HKtools.LOGGER.info(item.getDisplayName());
                    break;
                case "tab":
                    HKtools.LOGGER.info(Utils.getTablist().toString());
                    break;
                case "where":
                    if (Utils.isInDungeons()) {
                        HKtools.LOGGER.info("in dungeons f" + Utils.dungeonFloor());
                    }
                    else if (Utils.isInGarden()) {
                        HKtools.LOGGER.info("in garden");
                    }
                    else {
                        HKtools.LOGGER.info("in dreamland");
                    }
                    break;
            }
        }
    }
}
