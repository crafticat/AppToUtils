package mc.apptoeat.com.utils.ai.PathFinding;

import mc.apptoeat.com.utils.data.DataLocation;
import mc.apptoeat.com.utils.shortcuts.WorldUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import javax.xml.crypto.Data;

public class PathFinder {

    public static Vector newMotion(DataLocation from, Vector lastMotion,double speed, World world) {
        double friction = WorldUtils.getFrictionFromDataLoc(from,world);
        Vector lastMot = lastMotion.multiply(friction);

        from.addVector(lastMot.clone());
        Location loc = new Location(world,from.getX() ,from.getY() ,from.getZ(),from.getYaw(),0);

        Vector motion = lastMot.clone();
        motion.add(loc.getDirection().normalize().multiply(speed));

        return motion;
    }
}
