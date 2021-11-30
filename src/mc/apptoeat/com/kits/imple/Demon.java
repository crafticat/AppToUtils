package mc.apptoeat.com.kits.imple;

import mc.apptoeat.com.items.loadItems;
import mc.apptoeat.com.kits.kit;
import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.FastMath;
import mc.apptoeat.com.utils.color;
import mc.apptoeat.com.utils.managers.DataPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Demon extends kit {
    public Demon(String name, ItemStack guiItem, ArrayList<String> loreAbility, Integer cooldown, ItemStack abilityItem) {
        super(name,guiItem,loreAbility,cooldown,abilityItem);
    }

    @Override
    public void giveInv(Player player, String name) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);
        giveBasicInv(name,player);
        data.currectKit = this;
        player.getInventory().setItem(8, loadItems.Demon);
    }

    @Override
    public void activateAbility(Player player) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);

        if (data.currectKit == this) {
            double cooldown = getCooldown() - ((System.currentTimeMillis() - data.DemonAbilityUse) / 1000);
            if (cooldown <= 0 && data.lastHitEntity != null) {
                player.sendMessage("&fActiving &b&l" + name + " &fability");
                data.DemonAbilityUse = System.currentTimeMillis();
                //TODO active ability
                Location location = player.getLocation();

                try {
                    new BukkitRunnable() {
                        double t = 0;
                        public void run() {
                            t += Math.PI/8;
                            Location clonedFrom;
                            if (data.lastSoulLocation == null) {
                                clonedFrom = player.getLocation().clone();
                            } else clonedFrom = data.lastSoulLocation;
                            Vector startVector = clonedFrom.toVector();
                            Vector targetVector = data.lastHitEntity.getLocation().toVector();
                            clonedFrom.setDirection(targetVector.subtract(startVector));
                            Vector direction = clonedFrom.getDirection().normalize();

                            double x = direction.getX() * 0.75 * t;
                            double y = direction.getY() * 0.75 * t + 1.5;
                            double z = direction.getZ() * 0.75 * t;

                            location.add(x,y,z);
                            data.lastSoulLocation = location;
                            //FastMath.spawnParticle(EnumParticle.FIREWORKS_SPARK,0,5,location,player);
                            FastMath.spawnColoredParticle(EnumParticle.REDSTONE,0,20,location,player,47,47,47);
                            for (Entity entity : location.getChunk().getEntities()) {
                                if (entity.getName() != player.getName()) {
                                    if (entity.getLocation().distance(location) < 1.9) {
                                        entity.setFireTicks(20 * 2);

                                        Vector newDirection = new Vector(direction.getX() * 1.5, 0.5, direction.getZ() * 1.5);

                                        entity.setVelocity(newDirection);

                                        if (entity instanceof Player) {
                                            Player player1 = (Player) entity;
                                            player1.damage(7);
                                        }

                                        data.lastSoulLocation = null;
                                        this.cancel();
                                        return;
                                    }
                                }
                            }
                            location.subtract(x,y,z);


                            if (t >= 25*Math.PI) {
                                data.lastSoulLocation = null;
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(main.getInstance(),0,1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else player.sendMessage(color.code("&7ability on cooldown please wait &c&l" + cooldown + " &7seconds!"));
        }
    }

    public static double calculateYawDifference(Location from, Location to) {
        Location clonedFrom = from.clone();
        Vector startVector = clonedFrom.toVector();
        Vector targetVector = to.toVector();
        clonedFrom.setDirection(targetVector.subtract(startVector));
        return clonedFrom.getYaw();
    }


}
