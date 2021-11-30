package mc.apptoeat.com;

import lombok.Getter;
import mc.apptoeat.com.utils.managers.DataManager;
import mc.apptoeat.com.utils.managers.EventManager;
import mc.apptoeat.com.utils.managers.ItemAbilityManager;
import mc.apptoeat.com.utils.packets.PacketsListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class core extends JavaPlugin {
    @Getter
    private static core instance;
    private EventManager eventManager;
    private DataManager dataManager;
    private ItemAbilityManager itemAbilityManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        setManagers();
        getOnlinePlayers();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setManagers() {
        eventManager = new EventManager();
        dataManager = new DataManager();
        itemAbilityManager = new ItemAbilityManager();

        Bukkit.getPluginManager().registerEvents(new PacketsListener(),this);
    }

    public void getOnlinePlayers() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            dataManager.add(p.getPlayer());
        }
    }
}
