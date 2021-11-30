package mc.apptoeat.com.utils.data;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DataPlayer {
    public Player player;

    public DataLocation lastPosUpdate;

    public DataPlayer(Player player) {
        this.player = player;
    }
}
