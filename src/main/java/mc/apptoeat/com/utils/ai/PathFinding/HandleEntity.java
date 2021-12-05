package mc.apptoeat.com.utils.ai.PathFinding;

import mc.apptoeat.com.utils.ai.NPC;
import mc.apptoeat.com.utils.shortcuts.AABB;
import mc.apptoeat.com.utils.shortcuts.EntityUtils;
import mc.apptoeat.com.utils.shortcuts.ParticleUtils;
import mc.apptoeat.com.utils.shortcuts.WorldUtils;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleEntity {

    public static Entity[] closeEntitys(Location loc) {
        return loc.getChunk().getEntities();
    }

    public static void check(Location location, Player target, World world,NPC npc) {
        AABB aabb = new AABB(location.toVector().subtract(new Vector(1,0,1)),location.toVector().add(new Vector(1,2,1)));
        Arrays.stream(closeEntitys(location)).forEach(entity -> {
            if (EntityUtils.isShootAble(entity) && aabb.isColliding(entity.getLocation().toVector())) {
                float yaw = target.getLocation().getYaw();
                entity.remove();
                NPCDamage.sendDamage(npc,target,yaw);
                NPCDamage.lastDamage.put(npc.getNpc().getId(),System.currentTimeMillis());
            }
        });
    }
}
