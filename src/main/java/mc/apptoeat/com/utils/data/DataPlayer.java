package mc.apptoeat.com.utils.data;

import mc.apptoeat.com.utils.npc.NPC;
import org.bukkit.entity.Player;

public class DataPlayer {
    public Player player;

    public DataLocation lastPosUpdate;

    public NPC npc;

    public long guiCooldown;

    public DataPlayer(Player player) {
        this.player = player;
    }
}
