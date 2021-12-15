package mc.apptoeat.com.utils.objects;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface PlayerRunnable {

    public abstract void run(Player player);
}
