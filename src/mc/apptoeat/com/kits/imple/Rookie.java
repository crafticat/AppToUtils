package mc.apptoeat.com.kits.imple;

import mc.apptoeat.com.items.loadItems;
import mc.apptoeat.com.kits.kit;
import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.FastMath;
import mc.apptoeat.com.utils.managers.DataPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Rookie extends kit {
    public Rookie(String name, ItemStack guiItem, ArrayList<String> loreAbility, Integer cooldown, ItemStack abilityItem) {
        super(name,guiItem,loreAbility,cooldown,abilityItem);
    }

    @Override
    public void giveInv(Player player, String name) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);
        giveBasicInv(name,player);
        data.currectKit = this;
        player.getInventory().setItem(8, loadItems.Rookie);
    }

    @Override
    public void activateAbility(Player player) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);
        Location location = player.getLocation();
        new BukkitRunnable() {
            double phi = 0;
            public void run() {
                phi += Math.PI/10;
                for (double t = 0; t <= 2*Math.PI; t += Math.PI/40) {
                    double r = 1.5;
                    double x = r*Math.cos(t)*Math.sin(phi);
                    double y = r*Math.cos(phi) + 1.5;
                    double z = r*Math.sin(t)*Math.sin(phi);

                    if (player.getLocation().distance(location) < 3) {
                        data.inShield = true;
                    } else data.inShield = false;

                    location.add(x,y,z);
                    FastMath.spawnParticle(EnumParticle.DRIP_LAVA,0,5,location,player);
                    location.subtract(x,y,z);
                }

                if (phi > 4 * Math.PI) {
                    this.cancel();
                    data.inShield = false;
                }
            }
        }.runTaskTimer(main.getInstance(), 0, 1);
    }
}
