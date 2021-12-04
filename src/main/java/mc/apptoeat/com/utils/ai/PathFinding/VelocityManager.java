package mc.apptoeat.com.utils.ai.PathFinding;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

@Getter
@Setter
public class VelocityManager {

    private static double x;
    private static double y;
    private static double z;

    public VelocityManager(Vector vector) {
        x = vector.getX();
        y = vector.getY();
        z = vector.getZ();
    }

    public void setVelocity(Vector vector) {
        x = vector.getX();
        y = vector.getY();
        z = vector.getZ();
    }

    public Vector getVelocity() {
        return new Vector(x,y,z);
    }

    public void tick() {
        if (y > 0) {
            y -= 0.08D;
            y *= 0.98F;
        }
        if (x > 0) {
            x *= 0.91F;
        }
        if (z > 0) {
            z *= 0.91F;
        }


        if (y < 0.005) {
            y = 0;
        }
        if (x < 0.005)
            x = 0;
        if (z < 0.005)
            z = 0;
    }
}
