package mc.apptoeat.com.utils.ai.PathFinding;

import mc.apptoeat.com.utils.data.DataLocation;
import mc.apptoeat.com.utils.shortcuts.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PathFinder {
    public static HashMap<Integer, Vector> lastMotion = new HashMap<>();
    public static HashMap<Integer,Double> lastMotionY = new HashMap<>();

    public static Vector newMotion(DataLocation from, Vector lastMotion, double speed, World world, Integer UUID, boolean ground,Vector velocity,float dirOffSet,double reduceNum) {
        double friction = WorldUtils.getFrictionFromDataLoc(from,world);
        Vector lastMot = lastMotion.multiply(friction);
        lastMot.multiply(reduceNum);
        //TODO add reduce Number hitSlowDown

        boolean onGround = from.toLocation(world).toVector().add(new Vector(0,-0.01,0)).toLocation(world).getBlock().getType() != Material.AIR;

        from.addVector(lastMot.clone());
        Location loc = new Location(world,from.getX() ,from.getY() ,from.getZ(),from.getYaw() + dirOffSet,0);

        if (velocity.getY() > 0) speed *= 0;
        if (!onGround) speed = 0.026;

        Vector motion = lastMot.clone();
        motion.add(loc.getDirection().normalize().multiply(speed));

        if (!onGround) {
            double motionY = (((lastMotionY.getOrDefault(UUID,0.0)) - 0.08D) * 0.9800000190734863D);
            motion.setY(motionY);
        }

        motion.add(velocity);
        if (velocity.getY() != 0) {
            motion.setY(velocity.getY());

        }

        lastMotionY.put(UUID,motion.getY());

        return motion;
    }

    public static DataLocation minecraftMove(World world,Location currentLocation,Integer UUID,boolean onGround,Vector velocity,boolean takeGivenVelocity) {
        Vector lastMot = lastMotion.get(UUID).clone();

        if (!takeGivenVelocity) {
            velocity = new Vector(0,0,0);
        }

        DataLocation location = new DataLocation(currentLocation);
        Vector motion = PathFinder.newMotion(location.clone(), lastMot, 0.1, world, UUID, true, velocity.clone(), 0,0);
        lastMotion.put(UUID, motion);

        Vector newXYZ = checkForBlocksInWay(location, world, motion);
        location.setX(newXYZ.getX());
        location.setY(newXYZ.getY());
        location.setZ(newXYZ.getZ());

        return location;
    }

    public static Vector checkForBlocksInWay(DataLocation from, World world, Vector motion) {
        Location checkForBlocks = from.toLocation(world).toVector().add(motion).toLocation(world);
        boolean locIsBlock = checkForBlocks.getBlock().getType() != org.bukkit.Material.AIR;
        Vector location = from.toLocation(world).toVector().add(motion);

        if (locIsBlock) {
            double requireYtoBeOnGround = checkForBlocks.getBlock().getY() + 1;
            location.setY(requireYtoBeOnGround);
        }

        return location;
    }

}
