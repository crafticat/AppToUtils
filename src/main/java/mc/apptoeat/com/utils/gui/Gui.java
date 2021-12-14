package mc.apptoeat.com.utils.gui;

import lombok.Getter;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.shortcuts.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Gui extends Event {
    private final Inventory gui;
    private final HashMap<String,Runnable> itemAction = new HashMap<>();
    private final HashMap<String,Runnable> rightClick = new HashMap<>();
    private int size;
    private Player getClicker;

    public Gui(String title,int size) {
        gui = Bukkit.createInventory(null, size, Color.code(title));
        this.size = size;

        core.getInstance().getEventManager().getEvents().add(this);
    }

    @Override
    public void invClickEvent(Player player, InventoryClickEvent e) {
        try {
            if (e.getClick().isLeftClick()) {
                if (e.getInventory().getName().equals(gui.getName())) {
                    e.setCancelled(true);
                    String itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replace("&b&l", ""));
                    getClicker = (Player) e.getWhoClicked();
                    if (itemAction.containsKey(itemName)) itemAction.get(itemName).run();
                }
            }

            if (e.getClick().isRightClick()) {
                if (e.getInventory().getName().equals(gui.getName())) {
                    String itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replace("&b&l", ""));
                    e.setCancelled(true);
                    getClicker = (Player) e.getWhoClicked();
                    if (itemAction.containsKey(itemName)) rightClick.get(itemName).run();
                }
            }
        } catch (Exception ignore) {}
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(gui);
    }

    public void setRightClick(Runnable runnable,String name) {
        rightClick.put(name,runnable);
    }

    public void createGuiItem(final Material material, int slot, final String name,String color, Runnable action, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        itemAction.put(name,action);

        // Set the name of the item
        meta.setDisplayName(Color.code(color + name));

        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(Color.code(s));
        }

        // Set the lore of the item
        meta.setLore((newLore));

        item.setItemMeta(meta);
        
        gui.setItem(slot,item);
    }

    public void createGuiItem(final Material material,int amount,Byte id, int slot, final String name,String color, Runnable action, final String... lore) {
        final ItemStack item = new ItemStack(material, amount,id);
        final ItemMeta meta = item.getItemMeta();
        itemAction.put(name,action);

        // Set the name of the item
        meta.setDisplayName(Color.code(name));

        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(Color.code(s));
        }

        // Set the lore of the item
        meta.setLore((newLore));

        item.setItemMeta(meta);

        gui.setItem(slot,item);
    }

    public void setBackGroundColor(int color) {
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1,(short) color);
        for(int i = 0; i<size;i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i,glass);
            }
            if (gui.getItem(i) != null) {
                if (gui.getItem(i).getType() == Material.AIR) {
                    gui.setItem(i, glass);
                }
            }
        }
    }
}
