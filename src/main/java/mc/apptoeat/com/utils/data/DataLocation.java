package mc.apptoeat.com.utils.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;

@Getter
@Setter
public class DataLocation {
    private double x;
    private double y;
    private double z;
    private World world;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public DataLocation(double x,double y,double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
