package mc.apptoeat.com.utils.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

@Getter
@Setter
public class DataLocation {
    private double x;
    private double y;
    private double z;
    private World world;
    private float yaw = 1000;
    private float pitch = 1000;
    private boolean onGround;

    public DataLocation(double x,double y,double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public DataLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld();
    }

    public DataLocation clone() {
        return new DataLocation(x,y,z,yaw,pitch,world,onGround);
    }

    public void addVector(Vector vector) {
        x+= vector.getX();
        y+= vector.getY();
        z+= vector.getZ();
    }

    public Location toLocation(World world) {
        if (yaw == 1000) {
            return new Location(world, x, y, z);
        } else return new Location(world,x,y,z,yaw,pitch);
    }

    public DataLocation(double x,double y,double z,float yaw,float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public DataLocation(double x,double y,double z,float yaw,float pitch,World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public DataLocation(double x,double y,double z,float yaw,float pitch,World world,boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
        this.onGround = onGround;
    }
}
