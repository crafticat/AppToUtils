package mc.apptoeat.com.utils.managers;

import mc.apptoeat.com.utils.data.DataPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class DataManager {
    private final Set<DataPlayer> dataSet = new HashSet<>();

    public DataManager() {
        Bukkit.getOnlinePlayers().forEach(this::add);
    }

    public DataPlayer getDataPlayer(Player player) {
        return dataSet.stream().filter(dataPlayer -> dataPlayer.player == player).findFirst().orElse(null);
    }

    public void add(Player player) {
        dataSet.add(new DataPlayer(player));
    }

    public void remove(Player player) {
        dataSet.removeIf(dataPlayer -> dataPlayer.player == player);
    }
}
