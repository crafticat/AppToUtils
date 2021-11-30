package mc.apptoeat.com.utils.shortcuts;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Nms {

    public EntityPlayer getPlayer(Player player) {
        return  ((CraftPlayer) player).getHandle();
    }

    public void sendPacket(Player player, Packet packet) {
        getPlayer(player).playerConnection.sendPacket(packet);
    }
}
