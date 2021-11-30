package mc.apptoeat.com.utils;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class FastMath {
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

    public static double getRandom(double min,double max) {
        min = min * 100;
        max = max * 100;

        for(int i = (int) min; i <=max; i++) {
            double getRandomValue = ((int) (Math.random() * (max - min)) + min) / 100;
            return getRandomValue;
        }
        return 0;
    }


    public static void spawnColoredParticle(EnumParticle particle,int speed,float amount,Location location,Player player,int red,int green,int blue) {
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle,false, (float) location.getX(), (float) location.getY(),(float) location.getZ(),red,green,blue,amount,speed);
        for (Player players : player.getWorld().getPlayers()) {
            if (players.getLocation().distance(location) < 25) {
                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(particles);
            }
        }
    }
}
