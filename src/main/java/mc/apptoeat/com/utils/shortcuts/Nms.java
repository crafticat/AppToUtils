package mc.apptoeat.com.utils.shortcuts;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Nms {

    public static EntityPlayer getPlayer(Player player) {
        return  ((CraftPlayer) player).getHandle();
    }

    public static void sendPacket(Player player, Packet packet) {
        getPlayer(player).playerConnection.sendPacket(packet);
    }

    public static Entity getCraftEntity(org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static World getCraftWorld(org.bukkit.World world) {
        return ((CraftWorld) world).getHandle();
    }
}
