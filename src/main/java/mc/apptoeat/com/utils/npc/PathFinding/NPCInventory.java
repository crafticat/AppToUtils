package mc.apptoeat.com.utils.npc.PathFinding;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class NPCInventory {

    private ItemStack mainItem;
    private ItemStack helmet;
    private ItemStack chestPlace;
    private ItemStack leggings;
    private ItemStack boots;
    private Integer speedLevel;

    public NPCInventory(ItemStack mainItem,ItemStack helmet,ItemStack chestPlace,ItemStack leggings,ItemStack boots) {
        this.mainItem = mainItem;
        this.helmet = helmet;
        this.chestPlace = chestPlace;
        this.leggings = leggings;
        this.boots = boots;
    }
}
