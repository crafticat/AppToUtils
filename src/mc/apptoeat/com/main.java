package mc.apptoeat.com;

import mc.apptoeat.com.gui.openGui;
import mc.apptoeat.com.kits.kitManager;
import mc.apptoeat.com.utils.events.JoinQuitEvent;
import mc.apptoeat.com.utils.events.KillAndDeath;
import mc.apptoeat.com.utils.events.OnRightClick;
import mc.apptoeat.com.utils.managers.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    //Managers.
    private static main instance;
    private DataManager dataManager;
    private kitManager kitManager;
    private openGui gui;

    public void onEnable() {
        //enable stuff
        instance = this;
        this.dataManager = new DataManager();
        this.kitManager = new kitManager();

        gui = new openGui(5 * 9);

        //listeners
        this.getServer().getPluginManager().registerEvents(new JoinQuitEvent(),this);
        this.getServer().getPluginManager().registerEvents(new KillAndDeath(),this);
        this.getServer().getPluginManager().registerEvents(new OnRightClick(),this);

        //load items
        mc.apptoeat.com.items.loadItems.init();
    }

    public void onDisable() {
        //disable stuff
    }

    //Getter
    public DataManager getDataManager() {
        return dataManager;
    }

    public kitManager getKitManager() {
        return kitManager;
    }

    public static main getInstance() {
        return instance;
    }

    public openGui getGui() {
        return gui;
    }
}
