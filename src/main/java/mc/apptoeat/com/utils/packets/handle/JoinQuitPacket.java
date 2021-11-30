package mc.apptoeat.com.utils.packets.handle;

import mc.apptoeat.com.core;
import org.bukkit.entity.Player;

public class JoinQuitPacket {
    public static void join(Player player) {
        try {
            core.getInstance().getEventManager().getEvents().forEach(e -> {
                e.joinEvent(player);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void quit(Player player) {
        try {
            core.getInstance().getEventManager().getEvents().forEach(e -> {
                e.quitEvent(player);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
