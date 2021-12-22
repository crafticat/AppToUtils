package mc.apptoeat.com.utils.scoreboard;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.utils.shortcuts.Color;
import org.bukkit.Bukkit;
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

    public ScoreBoard(String title,final String... scoreboard) {
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();

        o = b.registerNewObjective(title, "");
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

        this.size = strings.size();
    }

    public void setToPlayer(Player player) {
        player.setScoreboard(b);
    }

    public void update(int line,String message) {
        int var1 = size - line + 1;
        Score score = o.getScore(Color.code(message));
        score.setScore(var1);
    }
}
