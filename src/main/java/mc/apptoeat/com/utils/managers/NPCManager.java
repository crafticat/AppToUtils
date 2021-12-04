package mc.apptoeat.com.utils.managers;

import lombok.Getter;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.ai.NPC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NPCManager {

    private List<NPC> npcs = new ArrayList<>();

    public static NPC getNpcFromPlayer(Player player) {
        for (NPC npc : core.getInstance().getNpcManager().getNpcs()) {
            if (npc.getTarget().getUniqueId() == player.getUniqueId()) {
                return npc;
            }
        }

        return null;
    }
}
