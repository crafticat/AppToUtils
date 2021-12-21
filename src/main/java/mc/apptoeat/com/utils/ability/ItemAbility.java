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
    private final String name;
    @Getter
    private final String colorCodes;

    public ItemAbility(Material material, String name, String colorCodes) {

        ItemStack gun = new ItemStack(material);
        ItemMeta meta = gun.getItemMeta();
        meta.setDisplayName(Color.code(colorCodes) + name);
        gun.setItemMeta(meta);

        this.item = gun;
        this.name = name;
        this.colorCodes = colorCodes;

        core.getInstance().getEventManager().getEvents().add(this);
    }

    @Override
    public void rightClickEvent(Player player, ItemStack clickedItem) {
        if (!clickedItem.getItemMeta().equals(item.getItemMeta())) return;
        rightClick(player, clickedItem);
    }

    public void rightClick(Player player, ItemStack clickedItem) {}
}
