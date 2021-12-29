package mc.apptoeat.com.utils.scoreboard;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.utils.shortcuts.Color;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ScoreBoard {
    private int size;
    private ScoreboardManager m;
    private Scoreboard b;
    private Objective o;
    private List<String> scoreBoard;
    private String name;
    private String title;

    public ScoreBoard(String title,String name,final String... scoreboard) {
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();

        o = b.registerNewObjective(name, "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(Color.code(title));

        List<String> strings = Arrays.asList(scoreboard);
        int i = 0;
        for (String s : strings) {
            int var1 = strings.size() - i;
            i++;
            Score score = o.getScore(Color.code(s));
            score.setScore(var1);
        }

        scoreBoard = strings;

        this.name = name;
        this.title = title;
        this.size = strings.size();
    }

    public void setToPlayer(Player player) {
        player.setScoreboard(b);
    }

    public void update(int line,String message,Player player) {
        int var1 = line - 1;
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();

        o = b.registerNewObjective(name, "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(Color.code(title));

        List<String> oldList = scoreBoard;
        int t = 0;
        for (String s : oldList) {
            t++;
            if (t == line) {
                oldList.remove(var1);
                oldList.set(var1,message);
            }
        }

        List<String> strings = oldList;
        int i = 0;
        for (String s : strings) {
            int var2 = strings.size() - i;
            i++;
            Score score = o.getScore(Color.code(s));
            score.setScore(var2);
        }

        scoreBoard = strings;

        this.size = strings.size();
        setToPlayer(player);
    }
}
