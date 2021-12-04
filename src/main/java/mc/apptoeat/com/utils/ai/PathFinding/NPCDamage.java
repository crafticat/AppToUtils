package mc.apptoeat.com.utils.ai.PathFinding;

import com.google.common.hash.HashCode;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.ai.NPC;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.shortcuts.MathUtils;
import mc.apptoeat.com.utils.shortcuts.Nms;
import mc.apptoeat.com.utils.shortcuts.WorldUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class NPCDamage extends Event {

    public NPCDamage() {

    }

    public static HashMap<Integer,Long> lastDamage = new HashMap<>();

    @Override
    public void attackEvent(Player player, int id) {
        //TODO add the packet
        //TODO check if id is currect
        //todo recode NPC class
        for (NPC npc : core.getInstance().getNpcManager().getNpcs()) {
            if (npc.getNpc().getId() == id) {
                if (System.currentTimeMillis() - lastDamage.getOrDefault(id,0L) > 475) {
                    sendDamage(npc,player);
                    lastDamage.put(id,System.currentTimeMillis());
                }
            }
        }
    }

    public void sendDamage(NPC npc,Player attacker) {
        npc.setVelocity(getVelocityAtDirection(attacker.getLocation().getYaw(), attacker.getWorld(),attacker,npc.getLastYUpdate(),npc.isReduce(),npc.getHVelocity(),npc.getVVelocity()));
        npc.setVelocityTaken(true);
        Nms.sendPacket(attacker,new PacketPlayOutAnimation(npc.getNpc(),1));
        attacker.playSound(attacker.getLocation(), Sound.HURT_FLESH, 100, 1);

        if (npc.getEntityDamageEvent() != null) {
            npc.getEntityDamageEvent().run();
        }
    }

    public Vector getVelocityAtDirection(float yaw, World world,Player attacker,long yUpdate,boolean reduce,double h,double v) {

        double hVelocity;
        if (h == 0) {
            hVelocity = 0.428;
        } else hVelocity = h;

        if (System.currentTimeMillis() - yUpdate > 200) {
            hVelocity *= 1.1;
        }

        if (attacker.isSprinting()) {
            hVelocity*=1.2;
        }
        if (reduce) {
            hVelocity*=0.6;
        }

        double vertical;
        if (v == 0) {
            vertical=0.31;
        } else vertical=v;

        Vector vector = new Location(world,0,0,0,yaw,0).getDirection().normalize().multiply(hVelocity);
        Vector motion = new Vector(vector.getX(),vertical,vector.getZ());

        return motion;
    }
}
