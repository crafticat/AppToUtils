package mc.apptoeat.com.utils.shortcuts;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class FileManager {

    public static void arraysSetVal(JavaPlugin main,ArrayList list,String path1,String path2,Object value,Boolean defaults) {
        list.forEach(item -> {
            if (defaults) {
                setDefault(main,path1 + "." + item + "." + path2,value);
            } else {
                main.getConfig().set(path1 + "." + item + "." + path2,value);
            }
        });
    }


    /* Great example of what it does */
    /*
    public static void example() {
        ArrayList<String> list = new ArrayList();
        list.add("cat1");
        list.add("cat2");
        arraysSetVal(main.getInstance(),list,"cats","settings.health","10",true);
    }
     */

    public static void setDefault(JavaPlugin main,String path,Object value) {
        if (!main.getConfig().isSet(path)) {
            main.getConfig().set(path,value);
            main.saveConfig();
        }
    }

    public static double getOrDefault(JavaPlugin main,String path,Double def) {
        if (!main.getConfig().isSet(path)) {
            main.getConfig().set(path,def);
            main.saveConfig();
            return def;
        } else {
            return main.getConfig().getDouble(path);
        }
    }
}
