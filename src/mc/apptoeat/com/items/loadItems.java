package mc.apptoeat.com.items;

import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class loadItems {
    public static ItemStack kitSelector;
    public static ItemStack Dragon;
    public static ItemStack EnderMan;
    public static ItemStack Flame;
    public static ItemStack Rookie;
    public static ItemStack Demon;
    public static ItemStack Inferno;
    
    public static void init() {

        createKitSelector();
        createDragon();
        createRookie();
        createDemon();
        createInferno();
    }

    private static void createKitSelector() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color.code("&8&l> &c&lKit Selector &8&l<"));
        item.setItemMeta(meta);
        kitSelector = item;
    }

    public static void createInferno() {
        ItemStack item = new ItemStack(Material.FIREBALL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color.code("&c&lInferno tower."));
        item.setItemMeta(meta);
        Inferno = item;
    }

    public static void createDemon() {
        ItemStack item = new ItemStack(Material.FIREBALL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color.code("&8&lSoul Slayer"));
        item.setItemMeta(meta);
        Demon = item;
    }


    public static void createRookie(){
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color.code("&8&lRookie Shield"));
        item.setItemMeta(meta);
        Rookie = item;
    }

    private static void createDragon() {
        ItemStack item = new ItemStack(Material.RED_ROSE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color.code("&8&lFlame Flower"));

        ArrayList<String> lore = main.getInstance().getKitManager().getKitByName("dragon").getLore();
        //TODO Recode role
        lore.add(color.code("&9Ability: Vanish &b&lRIGHT CLICK"));
        lore.add(color.code("&7Makes you Invisible for 5 seconds"));
        lore.add(color.code("&820 seconds cooldown"));
        lore.add("");
        lore.add(color.code("&8&lVanish Ability"));
        meta.setLore(lore);

        item.setItemMeta(meta);
        Dragon = item;
    }
}
