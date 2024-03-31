package com.smallpufferfish.hktools.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isInGarden() {
        List<String> lines = getScoreboardLines();
        for (String line : lines) {
            if (line.contains("The Garde\ud83c\udf20n") || line.contains("The Garden") || line.contains("Plot -")) return true;
        }
        return false;
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
            Pattern colorStripper = Pattern.compile("(?i)§[0-9A-FK-ORZ]");
            String cleansed1 = colorStripper.matcher(ScorePlayerTeam.formatPlayerName(team, line.getPlayerName()).trim()).replaceAll("");
            list.add(cleansed1);
        }
        Collections.reverse(list);
        return list;
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toArray(String[]::new);
    }
}
