package mc.apptoeat.com.utils.npc.PathFinding;

import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.npc.NPC;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.shortcuts.Nms;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class NPCDamage extends Event {
    public static HashMap<Integer,Long> lastDamage = new HashMap<>();

    @Override
    public void attackEvent(Player player, int id) {
        //TODO add the packet
        //TODO check if id is currect
        //todo recode NPC class
        for (NPC npc : core.getInstance().getNpcManager().getNpcs()) {
            if (npc.getNpc().getId() == id) {
                Nms.sendPacket(player, new PacketPlayOutAnimation(npc.getNpc(), 5));
                if (System.currentTimeMillis() - lastDamage.getOrDefault(id, 0L) > npc.getHitDelay()) {
                    sendDamage(npc,player);
                    lastDamage.put(id, System.currentTimeMillis());
                }
            }
        }
    }

    public void sendDamage(NPC npc, Player attacker) {
        double diff = 0;
        if (npc.getHitDelay() < 400) {
            diff = npc.getLocation().getY() - attacker.getLocation().getY();
        }
        npc.setVelocity(getVelocityAtDirection(attacker.getLocation().getYaw(), attacker.getWorld(), attacker, npc.getLastYUpdate(), npc.isReduce(), npc.getHVelocity(), npc.getVVelocity(), true,diff));
        npc.setVelocityTaken(true);
        Nms.sendPacket(attacker, new PacketPlayOutAnimation(npc.getNpc(), 1));
        attacker.playSound(attacker.getLocation(), Sound.HURT_FLESH, 100, 1);

        if (npc.getEntityDamageEvent() != null) {
            npc.getEntityDamageEvent().run();
        }
    }

    public static void sendDamage(NPC npc,Player attacker,float yaw) {
        double diff = 0;
        if (npc.getHitDelay() < 400) {
            diff = npc.getLocation().getY() - attacker.getLocation().getY();
        }
        npc.setVelocity(getVelocityAtDirection(yaw, attacker.getWorld(), attacker, npc.getLastYUpdate(), npc.isReduce(), npc.getHVelocity(), npc.getVVelocity(), false,diff));
        npc.setVelocityTaken(true);
        Nms.sendPacket(attacker,new PacketPlayOutAnimation(npc.getNpc(),1));
        attacker.playSound(attacker.getLocation(), Sound.HURT_FLESH, 100, 1);
    }

    public static Vector getVelocityAtDirection(float yaw, World world, Player attacker, long yUpdate, boolean reduce, double h, double v, boolean checkForSprinting,double yDiff) {
        double vertical;
        double hVelocity;

        if (v == 0) {
            vertical = 0.31;
        } else vertical = v;

        if (h == 0) {
            hVelocity = 0.428;
        } else {
            hVelocity = h;
        }

        if (yDiff + vertical > 2.5) {
            vertical = 2.5 - yDiff;
            hVelocity = 0.075;
        }

        if (System.currentTimeMillis() - yUpdate > 200 && yDiff == 0) {
            hVelocity *= 1.1;
        }

        if (attacker.isSprinting() && checkForSprinting) {
            hVelocity *= 1.0;
            if (yDiff != 0) {
                hVelocity *= 1.2;
            }
        }
        if (reduce) {
            hVelocity *= 0.6;
        }

        Vector vector = new Location(world, 0, 0, 0, yaw,0).getDirection().normalize().multiply(hVelocity);
        return new Vector(vector.getX(), vertical, vector.getZ());
    }
}
