package mc.apptoeat.com.utils.filemanager.objects;

import lombok.Getter;
import mc.apptoeat.com.utils.filemanager.FileManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

@Getter
public class DoubleFile {
    private double num;
    private final String path;
    private final Plugin main;

    public DoubleFile(Double def, String path, Plugin mainClass) {
        this.path = path;
        num = FileManager.getOrDefault((JavaPlugin) mainClass,path,def);
        this.main = mainClass;

        mainClass.getConfig().set(path,num);
        main.saveConfig();
    }

    public void add(double var1) {
        num += var1;
        main.getConfig().set(path,num);
        main.saveConfig();
    }

    public void subtract(double var1) {
        num -= var1;
        main.getConfig().set(path,num);
        main.saveConfig();
    }

    public void set(double var1) {
        num = var1;
        main.getConfig().set(path,num);
        main.saveConfig();
    }
}
