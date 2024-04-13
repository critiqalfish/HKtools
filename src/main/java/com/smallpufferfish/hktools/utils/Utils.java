package com.smallpufferfish.hktools.utils;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.storage.MapData;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern emojiStripper = Pattern.compile("\uD83C\uDF6B|\uD83D\uDCA3|\uD83D\uDC7D|\uD83D\uDD2E|\uD83D\uDC0D|\uD83D\uDC7E|\uD83C\uDF20|\uD83C\uDF6D|\u26BD|\uD83C\uDFC0|\uD83D\uDC79|\uD83C\uDF81|\uD83C\uDF89|\uD83C\uDF82|\uD83D\uDD2B");
    private static final Pattern colorStripper = Pattern.compile("(?i)§[0-9A-FK-ORZ]");

    public static boolean isInGarden() {
        List<String> lines = getScoreboardLines();
        for (String line : lines) {
            if (line.contains("The Garden") || line.contains("Plot -")) return true;
        }
        return false;
    }

    public static boolean isInDungeons() {
        List<String> lines = getScoreboardLines();
        for (String line : lines) {
            if (line.contains("The Catacombs (") && !line.contains("Queue")) return true;
        }
        return false;
    }

    public static int dungeonFloor() {
        List<String> lines = getScoreboardLines();
        for (String line : lines) {
            if (line.contains("The Catacombs (") && !line.contains("Queue")) {
                int i = line.lastIndexOf(")");
                if (i != -1) {
                    char f = line.charAt(i - 1);
                    if (f >= 49 && f <= 55) return f - 48;
                }
            }
        }
        return 0;
    }

    public static List<String> getScoreboardLines() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.theWorld == null) return Collections.emptyList();
        Scoreboard sb = mc.theWorld.getScoreboard();
        if (sb == null) return Collections.emptyList();
        ScoreObjective obj = sb.getObjectiveInDisplaySlot(1);
        Collection<Score> scores = sb.getSortedScores(obj);
        List<String> list = new ArrayList<>();
        for (Score line : scores) {
            ScorePlayerTeam team = sb.getPlayersTeam(line.getPlayerName());
            String cleansed = clean(ScorePlayerTeam.formatPlayerName(team, line.getPlayerName()));
            list.add(cleansed);
        }
        Collections.reverse(list);
        return list;
    }

    public static Integer[][] getMap(ItemStack mapStack) {
        MapData data = ((ItemMap) mapStack.getItem()).getMapData(mapStack, Minecraft.getMinecraft().theWorld);
        Integer[][] map = new Integer[128][128];
        for (int i = 0; i < 16384; i++) {
            int x = i % 128;
            int y = i / 128;
            int j = data.colors[i] % 255;
            int rgba;
            if (j / 4 == 0) {
                rgba = (i + 1 / 128 & 1) * 8 + 16 << 24;
            } else {
                rgba = MapColor.mapColorArray[j / 4].getMapColor(j & 3);
            }
            map[x][y] = rgba & 0x00FFFFFF;
        }
        return map;
    }

    public static List<String> getTablist() {
        Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
        ArrayList<String> tablist = new ArrayList<String>();
        for (NetworkPlayerInfo info : players) {
            String cleansed = clean(Minecraft.getMinecraft().ingameGUI.getTabList().getPlayerName(info));
            tablist.add(cleansed);
        }
        return tablist;
    }

    public static String clean(String str) {
        str = str.trim();
        str = emojiStripper.matcher(str).replaceAll("");
        str = colorStripper.matcher(str).replaceAll("");
        return str;
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toArray(String[]::new);
    }
}
