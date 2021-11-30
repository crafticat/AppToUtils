package mc.apptoeat.com.utils.managers;

import mc.apptoeat.com.kits.kit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DataPlayer {
    public Player player;
    public kit currectKit;

    public long dragonAbilityUse;
    public long DemonAbilityUse;
    public long InfernoAbilityUse;
    public boolean inShield;
    public Entity lastHitEntity;
    public Location lastSoulLocation;

    public DataPlayer(Player player) {
        this.player = player;
    }
}
