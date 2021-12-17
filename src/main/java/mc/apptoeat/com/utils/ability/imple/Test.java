package mc.apptoeat.com.utils.ability.imple;

import mc.apptoeat.com.utils.ability.ItemAbility;
import org.bukkit.Material;

public class Test extends ItemAbility {
    public Test() {
        super(Material.STICK, "cat", "&b&l");
    }

    /*
    @Override
    public void rightClick(Player player, ItemStack clickedItem) {
        player.sendMessage("click");
        Gui gui = new Gui("test",9);
        DataPlayer data = core.getInstance().getDataManager().getDataPlayer(player);
        gui.createGuiItem(Material.IRON_INGOT, 1, "normal", new Runnable() {
            @Override
            public void run() {
                player.sendMessage("Spawned npc level normal");
                NPCInventory inventory = new NPCInventory(new ItemStack(Material.STONE_SWORD,1),null,null,null,null);
                NPC npc = new NPC(player.getLocation(), player.getName(), player.getWorld(),player,3,2.5,true,false,inventory);
            }
        });
        gui.createGuiItem(Material.GOLD_INGOT, 2, "hard", new Runnable() {
            @Override
            public void run() {
                player.sendMessage("Spawned npc level hard");
                NPCInventory inventory = new NPCInventory(new ItemStack(Material.IRON_SWORD,1),null,null,null,null);
                NPC npc = new NPC(player.getLocation(), player.getName(), player.getWorld(),player,2,2.8,true,true,inventory);
            }
        });

        gui.createGuiItem(Material.DIAMOND, 3, "insane", new Runnable() {
            @Override
            public void run() {
                player.sendMessage("Spawned npc level insane");
                NPCInventory inventory = new NPCInventory(new ItemStack(Material.DIAMOND_SWORD,1),new ItemStack(Material.DIAMOND_CHESTPLATE,1),null,null,null);
                NPC npc = new NPC(player.getLocation(), player.getName(), player.getWorld(),player,1,4,true,true,inventory);
            }
        });
        gui.openInventory(player);
    }

     */
}
