package mc.apptoeat.com.kits;

import mc.apptoeat.com.items.loadItems;
import mc.apptoeat.com.kits.imple.Demon;
import mc.apptoeat.com.kits.imple.Dragon;
import mc.apptoeat.com.kits.imple.Inferno;
import mc.apptoeat.com.kits.imple.Rookie;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class kitManager {
    private List<kit> kits = new ArrayList<>();

    public kitManager() {
        //todo add Kits.
        //Dragon
        ArrayList<String> dragonLore = new ArrayList<>();
        dragonLore.add("dragon Lore");
        kits.add(new Dragon("dragon",new ItemStack(Material.BLAZE_ROD),dragonLore,5, loadItems.Dragon));
        kits.add(new Rookie("rookie",new ItemStack(Material.IRON_CHESTPLATE),dragonLore,5,loadItems.Rookie));
        kits.add(new Demon("Demon",new ItemStack(Material.FIREBALL),dragonLore,5,loadItems.Demon));
        kits.add(new Inferno("Inferno",new ItemStack(Material.FIREBALL),dragonLore,5,loadItems.Inferno));
        //Shield master.
    }

    public List<kit> getKits() {
        return kits;
    }

    public kit getKitByName(String name) {
        return getKits().stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
