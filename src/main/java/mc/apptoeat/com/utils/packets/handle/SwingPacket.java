package mc.apptoeat.com.utils.packets.handle;

import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.data.DataLocation;
import mc.apptoeat.com.utils.data.DataPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Set;

public class SwingPacket {

    public static void handle(Player player, PacketPlayInArmAnimation animation) {
        try {
            boolean destroying = false;
            if(player.getTargetBlock((Set<Material>) null, 4).getType() != org.bukkit.Material.AIR){
                destroying = true;
            } else if (player.getTargetBlock((Set<Material>) null, 4).getType() == Material.AIR) {
                destroying = false;
            }

            if (!destroying) {
                core.getInstance().getEventManager().getEvents().forEach(event -> {
                    event.swing(player);
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
