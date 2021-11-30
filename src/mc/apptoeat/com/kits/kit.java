package mc.apptoeat.com.kits;

import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class kit implements Listener {
    protected String name;
    protected ItemStack guiItem;
    protected ArrayList<String> lore;
    protected int cooldown;
    protected ItemStack abilityItem;

    public kit(String name, ItemStack guiItem,ArrayList<String> loreAbility,Integer cooldown,ItemStack abilityItem) {
        this.name = name;
        this.guiItem = guiItem;
        this.lore = loreAbility;
        this.cooldown = cooldown;
        this.abilityItem = abilityItem;

        Bukkit.getServer().getPluginManager().registerEvents(this, main.getInstance());
    }

    //on click on inventory.
    public abstract void giveInv(Player player, String name);

    protected void giveBasicInv(String kitname,Player player) {
        ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = ironSword.getItemMeta();
        meta.setDisplayName(color.code("&7&l"+kitname+"Sword"));
        ironSword.setItemMeta(meta);

        player.getInventory().setItem(0, ironSword);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
    }

    //on RightClick item.
    public abstract void activateAbility(Player player);

    protected void giveAbilityItem(Player player) {
        player.getInventory().setItem(8,abilityItem);
    }

    public String getName() {
        return name;
    }

    public ItemStack getGuiItem() {
        return guiItem;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public ItemStack getAbilityItem() {
        return abilityItem;
    }

    public int getCooldown() {
        return cooldown;
    }
}
