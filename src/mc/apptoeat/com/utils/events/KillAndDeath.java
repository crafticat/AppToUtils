package mc.apptoeat.com.utils.events;

import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.managers.DataPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillAndDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = (Player) e.getEntity();
        player.getInventory().clear();
        e.getDrops().clear();

        //TODO Do death stuff to (player);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            DataPlayer data = main.getInstance().getDataManager().getDataPlayer((Player) e.getDamager());
            data.lastHitEntity = e.getEntity();
        }
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player target = (Player) e.getDamager();
            DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);

            if (data.inShield) e.setCancelled(true);

            //TODO do kill stuff to (target);
        }
    }
}
