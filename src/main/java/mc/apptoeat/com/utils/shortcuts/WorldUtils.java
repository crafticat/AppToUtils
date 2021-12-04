package mc.apptoeat.com.utils.shortcuts;

import mc.apptoeat.com.utils.data.DataLocation;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class WorldUtils {
    public static void spawnParticle(EnumParticle particle, int speed, float amount, Location location, Player player) {
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle,false, (float) location.getX(), (float) location.getY(),(float) location.getZ(),0,0,0,amount,speed);
        for (Player players : player.getWorld().getPlayers()) {
            if (players.getLocation().distance(location) < 25) {
                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(particles);
            }
        }
    }

    public static void spawnParticleWithRandom(EnumParticle particle, int speed, float amount, Location location, Player player,double red,double green,double blue) {
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle,true, (float) location.getX(), (float) location.getY(),(float) location.getZ(),(float) red,(float) green,(float) blue,0,(int) amount,0);
        for (Player players : player.getWorld().getPlayers()) {
            if (players.getLocation().distance(location) < 25) {
                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(particles);
            }
        };

    }

    public static boolean onGround(Location location) {
        Block block = new Location(location.getWorld(),location.getX(),location.getY() - 0.001,location.getZ()).getBlock();
        Bukkit.broadcastMessage("true");
        return true;
    }

    public static boolean nearBlock(Location location,Material material,double xzOffset,double yOffSet) {
        Location min = location.toVector().add(new Vector(xzOffset,1,xzOffset)).toLocation(location.getWorld());
        Location max = location.toVector().subtract(new Vector(xzOffset,yOffSet,xzOffset)).toLocation(location.getWorld());

        for (Block block : blocksFromTwoPoints(min,max)) {
            if (block.getType() == material) {
                return true;
            }
        }
        return false;
    }

    public static float calculateYawDifference(Location from, Location to) {
        Location clonedFrom = from.clone();
        Vector startVector = clonedFrom.toVector();
        Vector targetVector = to.toVector();
        clonedFrom.setDirection(targetVector.subtract(startVector));
        return clonedFrom.getYaw();
    }

    public static void spawnParticleWithOffSet(EnumParticle particle, int speed, float amount, Location location, Player player,Double offset) {
        for (int t = 0;t < amount;t++) {
            Location loc = location.toVector().add(new Vector(getRandom(-offset,offset),getRandom(-offset,offset),getRandom(-offset,offset))).toLocation(location.getWorld());
            PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle,false, (float) loc.getX(), (float) loc.getY(),(float) loc.getZ(),0,0,0,1,speed);
            for (Player players : player.getWorld().getPlayers()) {
                if (players.getLocation().distance(location) < 25) {
                    ((CraftPlayer) players).getHandle().playerConnection.sendPacket(particles);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static double getFrictionFromLoc(Location loc) {
        float friction = 0.91f;
        String getBlock = "stone";
        Location location = new Location(loc.getWorld(),loc.getX(),loc.getY() - 1,loc.getZ());

        if (location.getBlock().getType() == Material.AIR) getBlock = "air";
        if (location.getBlock().getType() == Material.SLIME_BLOCK) getBlock = "slime";
        if (location.getBlock().getType() == Material.ICE) getBlock = "ice";
        if (location.getBlock().getType() == Material.PACKED_ICE) getBlock = "ice";

        if (getBlock.equals("stone")) friction*=0.6;
        if (getBlock.equals("ice")) friction*=0.98;
        if (getBlock.equals("slime")) friction*=0.8;

        return friction;
    }

    @SuppressWarnings("deprecation")
    public static double getFrictionFromDataLoc(DataLocation loc, World world) {
        float friction = 0.91f;
        String getBlock = "stone";
        Location location = new Location(world,loc.getX(),loc.getY() - 0.01,loc.getZ());

        if (location.getBlock().getType() == Material.AIR) getBlock = "air";
        if (location.getBlock().getType() == Material.SLIME_BLOCK) getBlock = "slime";
        if (location.getBlock().getType() == Material.ICE) getBlock = "ice";
        if (location.getBlock().getType() == Material.PACKED_ICE) getBlock = "ice";

        if (getBlock.equals("stone")) friction*=0.6;
        if (getBlock.equals("ice")) friction*=0.98;
        if (getBlock.equals("slime")) friction*=0.8;

        return friction;
    }


    public static double getRandom(double min,double max) {
        min = min * 100;
        max = max * 100;

        for(int i = (int) min; i <=max; i++) {
            double getRandomValue = ((int) (Math.random() * (max - min)) + min) / 100;
            return getRandomValue;
        }
        return 0;
    }

    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }
}
