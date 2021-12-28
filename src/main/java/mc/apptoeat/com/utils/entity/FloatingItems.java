package mc.apptoeat.com.utils.entity;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class FloatingItems {
    private Location loc;
    private ArmorStand stand;

    public FloatingItems(Location loc, Player player,boolean head, ItemStack itemStack, boolean pass, Entity passer,float yaw,float pitch) {
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        ArmorStand container = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        container.setVisible(false);
        container.setGravity(false);
        container.setSmall(true);
        if (head) {
            container.getEquipment().setHelmet(itemStack);
        }
        if (pass) {
            container.setPassenger(passer);
        }
        container.setMarker(true);
        this.stand = container;
    }
}
