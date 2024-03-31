package com.smallpufferfish.hktools.commands;

import com.smallpufferfish.hktools.HKtools;
import com.smallpufferfish.hktools.gui.HKtoolsGUI;
import com.smallpufferfish.hktools.utils.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.Collections;
import java.util.List;

public class HKtoolsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "hktools";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    private static final String[] SUBCOMMANDS = {};

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
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("HKtools v" + HKtools.VERSION));
        new DelayedTask((() -> Minecraft.getMinecraft().displayGuiScreen(new HKtoolsGUI())), 1);
    }
}
