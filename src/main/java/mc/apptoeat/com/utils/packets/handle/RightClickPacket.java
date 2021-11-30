package mc.apptoeat.com.utils.packets.handle;

import mc.apptoeat.com.core;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RightClickPacket {
    public static void handle(Player player, ItemStack item) {
        //TODO change itemStack import
        try {
            core.getInstance().getEventManager().getEvents().forEach(event -> {
                event.rightClickEvent(player,item);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
