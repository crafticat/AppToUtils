package mc.apptoeat.com.utils.filemanager.objects;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

@Getter
public class DoubleFile {
    private double num;
    private String path;
    private Plugin main;

    public DoubleFile(Double def, String path, Plugin mainClass) {
        this.num = def;
        this.path = path;
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
