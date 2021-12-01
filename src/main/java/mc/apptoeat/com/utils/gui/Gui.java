package mc.apptoeat.com.utils.gui;

import lombok.Getter;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.shortcuts.Color;
import org.bukkit.Bukkit;
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
    private Inventory gui;
    private HashMap<String,Runnable> itemAction = new HashMap<>();

    public Gui(String title,int size) {
        gui = Bukkit.createInventory(null,size, Color.code(title));

        core.getInstance().getEventManager().getEvents().add(this);
    }

    @Override
    public void invClickEvent(Player player, InventoryClickEvent e) {
        if (e.getInventory().getName() == gui.getName()) {
            String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
            e.setCancelled(true);
            if (itemAction.containsKey(itemName)) {
                itemAction.get(itemName).run();
            }
        }
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(gui);
    }

    public void createGuiItem(final Material material,int slot, final String name,Runnable action, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        itemAction.put(name,action);

        // Set the name of the item
        meta.setDisplayName(name);

        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(Color.code(s));
        }

        // Set the lore of the item
        meta.setLore((newLore));

        item.setItemMeta(meta);

        gui.addItem(item);
    }
}
