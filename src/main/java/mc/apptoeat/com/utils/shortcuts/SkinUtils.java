package mc.apptoeat.com.utils.shortcuts;

import org.bukkit.Bukkit;

import java.util.UUID;

public class SkinUtils {

    @Deprecated
    public static UUID getUUIDFromName(String name) {
        return UUID.fromString(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
    }
}
