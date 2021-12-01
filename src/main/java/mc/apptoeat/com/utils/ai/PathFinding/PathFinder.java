package mc.apptoeat.com.utils.ai.PathFinding;

import mc.apptoeat.com.utils.data.DataLocation;
import mc.apptoeat.com.utils.shortcuts.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class PathFinder {
    public static HashMap<Integer, Vector> lastMotion = new HashMap<>();
    public static HashMap<Integer,Double> lastMotionY = new HashMap<>();

    public static Vector newMotion(DataLocation from, Vector lastMotion,double speed, World world,int id) {
        double friction = WorldUtils.getFrictionFromDataLoc(from,world);
        Vector lastMot = lastMotion.multiply(friction);

        from.addVector(lastMot.clone());
        Location loc = new Location(world,from.getX() ,from.getY() ,from.getZ(),from.getYaw(),0);

        Vector motion = lastMot.clone();
        motion.add(loc.getDirection().normalize().multiply(speed));

        Location checkForBlocks = from.toLocation(world).toVector().add(motion).toLocation(world);
        boolean locIsBlock = checkForBlocks.getBlock().getType() != Material.AIR;
        boolean onGround = friction != 0.91;

        if (locIsBlock) {
            motion.add(new Vector(0,0.1,0));
        }
        if (!onGround) {
            double motionY = ((lastMotionY.get(id)) - 0.08D) * 0.9800000190734863D;
            motion.add(new Vector(0,motionY,0));
        }
        lastMotionY.put(id,motion.getY());

        return motion;
    }

    public static Location minecraftMove(World world,Location currentLocation,int id) {
        Vector lastMot = lastMotion.getOrDefault(id,new Vector(0,0,0));
        Vector motion = PathFinder.newMotion(new DataLocation(currentLocation),lastMot,0.1,world,id);
        lastMotion.put(id,motion);
        return currentLocation.toVector().add(motion).toLocation(world);
    }
}
