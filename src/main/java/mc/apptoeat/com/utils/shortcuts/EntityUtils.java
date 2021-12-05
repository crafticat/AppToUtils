package mc.apptoeat.com.utils.shortcuts;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityUtils {

    public static boolean isShootAble(Entity entity) {
        if (entity.getType() == EntityType.SNOWBALL) return true;
        if (entity.getType() == EntityType.EGG) return true;
        if (entity.getType() == EntityType.FISHING_HOOK) return true;
        if (entity.getType() == EntityType.ARROW) return true;

        return false;
    }
}
