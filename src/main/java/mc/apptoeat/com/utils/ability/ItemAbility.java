package mc.apptoeat.com.utils.ability;

import lombok.Getter;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.shortcuts.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemAbility extends Event {
    @Getter
    protected ItemStack item;
    @Getter
    private String name;

    public ItemAbility(Material material, String name) {

        ItemStack gun = new ItemStack(material);
        ItemMeta meta = gun.getItemMeta();
        meta.setDisplayName(Color.code(name));
        gun.setItemMeta(meta);

        this.item = gun;
        this.name = name;

        core.getInstance().getEventManager().getEvents().add(this);
    }

    @Override
    public void rightClickEvent(Player player, ItemStack clickedItem) {
        rightClick(player,clickedItem);
    }

    public void rightClick(Player player, ItemStack clickedItem) {}
}
