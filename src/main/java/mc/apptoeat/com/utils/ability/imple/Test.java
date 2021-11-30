package mc.apptoeat.com.utils.ability.imple;

import mc.apptoeat.com.utils.ability.ItemAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Test extends ItemAbility {
    public Test() {
        super(Material.STICK, "cat", "&b&l");
    }

    public void rightClick(Player player, ItemStack clickedItem) {
        player.sendMessage("click");
    }
}
