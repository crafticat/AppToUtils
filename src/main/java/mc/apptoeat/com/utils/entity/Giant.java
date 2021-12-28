package mc.apptoeat.com.utils.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

@Getter
@Setter
public class Giant {
    private org.bukkit.entity.Giant giant;

    public Giant(Location loc) {
        org.bukkit.entity.Giant giant = (org.bukkit.entity.Giant) loc.getWorld().spawnEntity(loc, EntityType.GIANT);
    }
}
