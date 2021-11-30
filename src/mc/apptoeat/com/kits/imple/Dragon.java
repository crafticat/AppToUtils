package mc.apptoeat.com.kits.imple;

import mc.apptoeat.com.items.loadItems;
import mc.apptoeat.com.kits.kit;
import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.color;
import mc.apptoeat.com.utils.managers.DataPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Dragon extends kit {
    public Dragon(String name, ItemStack guiItem, ArrayList<String> loreAbility, Integer cooldown, ItemStack abilityItem) {
        super(name,guiItem,loreAbility,cooldown,abilityItem);
    }

    @Override
    public void giveInv(Player player, String name) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);
        giveBasicInv(name,player);
        data.currectKit = this;
        player.getInventory().setItem(8, loadItems.Dragon);
    }

    @Override
    public void activateAbility(Player player) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);

        if (data.currectKit == this) {
            double cooldown = getCooldown() - ((System.currentTimeMillis() - data.dragonAbilityUse) / 1000);
            if (cooldown <= 0) {
                player.sendMessage("&fActiving &b&l" + name + " &fability");
                //TODO do particles.
                data.dragonAbilityUse = System.currentTimeMillis();
            }  else player.sendMessage(color.code("&7ability on cooldown please wait &c&l" + cooldown + " &7seconds!"));
        }
    }
}
