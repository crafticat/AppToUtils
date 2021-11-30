package mc.apptoeat.com.utils.events;

import lombok.Getter;
import mc.apptoeat.com.utils.data.DataLocation;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Event {

    @Getter
    private String name;

    /* Events */
    public void moveEvent(Player player, DataLocation to, DataLocation from, PacketPlayInFlying wrapper) {}

    public void attackEvent(Player player) {}

    public void rightClickEvent(Player player, ItemStack clickedItem) {}

    public void invClickEvent(Player player, InventoryClickEvent e) {}

    public void joinEvent(Player player) {}

    public void quitEvent(Player player) {}
}
