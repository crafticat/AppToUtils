package mc.apptoeat.com.utils.packets.handle;

import mc.apptoeat.com.core;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Optional;

public class AttackPacket {

    public static void handle(Player player, PacketPlayInUseEntity wrapper) {
        try {
            PacketPlayInUseEntity.EnumEntityUseAction action = wrapper.a();

            World world = ((CraftWorld)player.getWorld()).getHandle();

            if (wrapper.a(world) != null) {
                int id = wrapper.a(world).getId();
                Optional<Entity> entityOp = player.getWorld().getEntities().stream().filter(entity -> entity.getEntityId() == id).findFirst();

                if (entityOp.isPresent()) {
                    Entity entity = entityOp.get();
                    if (entity instanceof LivingEntity && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.ATTACK)) {
                        try {
                            LivingEntity entity1 = (LivingEntity) entity;

                            if (action.equals(PacketPlayInUseEntity.EnumEntityUseAction.ATTACK)) {
                                //AttackPacketRight
                            }
                        } catch (Exception exception) {
                        }
                    }
                }

            }


            Field f = wrapper.getClass().getDeclaredField("a");
            f.setAccessible(true);

            int id = (int) f.get(wrapper);

            if (action.equals(PacketPlayInUseEntity.EnumEntityUseAction.ATTACK)) {
                core.getInstance().getEventManager().getEvents().forEach(check -> {
                    check.attackEvent(player,id);
                });
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
