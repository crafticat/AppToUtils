package mc.apptoeat.com.utils.ai;

import com.mojang.authlib.GameProfile;
import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.ai.PathFinding.PathFinder;
import mc.apptoeat.com.utils.data.DataLocation;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.shortcuts.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

@Getter
@Setter
public class NPC extends Event {

    private EntityPlayer npc;
    private DataLocation location;
    private static DataLocation to;
    private Vector lastMotion = new Vector(0, 0, 0);
    private World world;
    private DataLocation oldLoc;
    private int ticks;
    private Player target;

    public NPC(Location loc, String name, World w,Player target) {
        if (name.length() < 16) {
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) w).getHandle();
            UUID uUID;
            GameProfile profile = new GameProfile(UUID.randomUUID(), Color.code(name));

            npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
            npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            location = new DataLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            oldLoc = location;
            this.world = w;
            this.target = target;
            sendCreatesPackets();

            core.getInstance().getEventManager().getEvents().add(this);
            StartTicking();
        } else Bukkit.broadcastMessage(Color.code("&cError&f: npc max letters is 16 letters -> &7" + name.length()));
    }

    public void setPath(Location loc) {
        to = new DataLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch());
    }

    public void updatePos() {
        Vector lastMot = lastMotion.clone();
        Vector motion = PathFinder.newMotion(location.clone(),lastMot,0.1,world,npc.getId());
        lastMotion = motion;
        location.addVector(motion);
    }

    public void StartTicking() {
        core.getInstance().getSyncTaskScheduler().addRepeatingTask(new Runnable() {
            @Override
            public void run() {
                setPath(target.getLocation());
                if (to != null) {

                    Location from = new Location(world,location.getX(),location.getY(),location.getZ());
                    Location t = new Location(world,to.getX(),to.getY(),to.getZ());
                    location.setYaw((float) calculateYawDifference(from,t));

                    updatePos();
                    ticks++;
                    if (ticks >= 3) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            oldLoc = location;
                            Nms.sendPacket(player,new PacketPlayOutEntityTeleport(npc));
                            npc.setLocation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
                            WorldUtils.spawnParticle(EnumParticle.FLAME, 0, 1, location.toLocation(world), player);
                        }
                        ticks = 0;
                    }
                }
            }
        },1);
    }

    public static double calculateYawDifference(Location from, Location to) {
        Location clonedFrom = from.clone();
        Vector startVector = clonedFrom.toVector();
        Vector targetVector = to.toVector();
        clonedFrom.setDirection(targetVector.subtract(startVector));
        return clonedFrom.getYaw();
    }

    private void sendCreatesPackets() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Nms.sendPacket(player,new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,npc));
            Nms.sendPacket(player,new PacketPlayOutNamedEntitySpawn(npc));
            Nms.sendPacket(player,new PacketPlayOutEntityHeadRotation(npc,(byte) (npc.yaw * 256 / 360)));
        }
    }

    @Override
    public void joinEvent(Player player) {
        Nms.sendPacket(player,new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,npc));
        Nms.sendPacket(player,new PacketPlayOutNamedEntitySpawn(npc));
        Nms.sendPacket(player,new PacketPlayOutEntityHeadRotation(npc,(byte) (npc.yaw * 256 / 360)));
    }
}
