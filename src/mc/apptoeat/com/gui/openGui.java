package mc.apptoeat.com.gui;

import mc.apptoeat.com.kits.kit;
import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.color;
import mc.apptoeat.com.utils.managers.DataPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class openGui implements Listener {
    public final Inventory inv;
    private final Integer size;
    private String namecolor = "§b§l";

    public openGui(int size) {
        this.inv = Bukkit.createInventory(null,size, color.code("&c&LKit Selector"));
        this.size = size;

        main.getInstance().getServer().getPluginManager().registerEvents(this, main.getInstance());
        addItems();
    }

    public void addItems() {
        for(int i = 0; i<size;i++) {
            if(i < 9)
                inv.setItem(i,createGuiItem(Material.STAINED_GLASS_PANE, ""));
            if(i >size-10)
                inv.setItem(i,createGuiItem(Material.STAINED_GLASS_PANE, ""));
        }

        main.getInstance().getKitManager().getKits().forEach(kit -> {
            inv.addItem(createGuiItem(kit.getGuiItem().getType(), color.code(namecolor + kit.getName()), "","&bAbility&f: " + kit.getLore(),"&bcooldown&f: " + kit.getCooldown()));
        });
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    //TODO recode this.

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().getTitle().equalsIgnoreCase(inv.getTitle())) return;
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.isSimilar(new ItemStack(Material.AIR,1))) return;

        final Player p = (Player) e.getWhoClicked();
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(p.getPlayer());

        // Using slots click is a best option for your inventory click's
        kit c = main.getInstance().getKitManager().getKitByName(clickedItem.getItemMeta().getDisplayName().replace(namecolor,""));
        if(c != null) {
            c.giveInv(data.player, c.getName());

            p.sendMessage("click");
        }
    }

}
