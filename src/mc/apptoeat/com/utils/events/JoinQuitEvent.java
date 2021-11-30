package mc.apptoeat.com.utils.events;

import mc.apptoeat.com.items.loadItems;
import mc.apptoeat.com.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        main.getInstance().getDataManager().add(player);
        player.getInventory().setItem(0, loadItems.kitSelector);
        player.sendMessage("joined");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        main.getInstance().getDataManager().remove(e.getPlayer());
    }
}
