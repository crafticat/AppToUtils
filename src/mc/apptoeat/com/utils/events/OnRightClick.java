package mc.apptoeat.com.utils.events;

import mc.apptoeat.com.gui.openGui;
import mc.apptoeat.com.items.loadItems;
import mc.apptoeat.com.kits.kit;
import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.managers.DataPlayer;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnRightClick implements Listener {


    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);

        //returns to prevent errors.
        if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (e.getItem().getType() == Material.AIR) return;
        if (e.getItem() == null) return;

        ItemStack item = e.getItem();

        if (item.getItemMeta().getDisplayName() == loadItems.Dragon.getItemMeta().getDisplayName()) {
            main.getInstance().getKitManager().getKitByName("dragon").activateAbility(player);
        }

        if (item.getItemMeta().getDisplayName() == loadItems.kitSelector.getItemMeta().getDisplayName()) {
            main.getInstance().getGui().openInventory(player);
            player.sendMessage("clicked");
        }

        if (item.getItemMeta().getDisplayName() == loadItems.Rookie.getItemMeta().getDisplayName()) {
            main.getInstance().getKitManager().getKitByName("rookie").activateAbility(player);
            player.sendMessage("clicked");

        }

        if (item.getItemMeta().getDisplayName() == loadItems.Demon.getItemMeta().getDisplayName()) {
            main.getInstance().getKitManager().getKitByName("demon").activateAbility(player);
            player.sendMessage("click");
        }

        if (item.getItemMeta().getDisplayName() == loadItems.Inferno.getItemMeta().getDisplayName()) {
            main.getInstance().getKitManager().getKitByName("Inferno").activateAbility(player);
            player.sendMessage("click");
        }
    }
}
