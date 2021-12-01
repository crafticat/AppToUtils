package mc.apptoeat.com.utils.ability.imple;

import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.ability.ItemAbility;
import mc.apptoeat.com.utils.ai.NPC;
import mc.apptoeat.com.utils.data.DataPlayer;
import mc.apptoeat.com.utils.gui.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Test extends ItemAbility {
    public Test() {
        super(Material.STICK, "cat", "&b&l");
    }

    @Override
    public void rightClick(Player player, ItemStack clickedItem) {
        player.sendMessage("click");
        Gui gui = new Gui("test",9);
        DataPlayer data = core.getInstance().getDataManager().getDataPlayer(player);
        gui.createGuiItem(Material.STONE, 1, "1", new Runnable() {
            @Override
            public void run() {
                player.sendMessage("click1");
                 data.npc = new NPC(player.getLocation(), player.getName(), player.getWorld(),player);
            }
        });
        gui.createGuiItem(Material.IRON_SWORD, 2, "2", new Runnable() {
            @Override
            public void run() {
                player.sendMessage("click2");
                data.npc.setPath(player.getLocation());
            }
        });
        gui.openInventory(player);
    }
}
