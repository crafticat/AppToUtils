package mc.apptoeat.com.kits.imple;

import mc.apptoeat.com.items.loadItems;
import mc.apptoeat.com.kits.kit;
import mc.apptoeat.com.main;
import mc.apptoeat.com.utils.FastMath;
import mc.apptoeat.com.utils.managers.DataPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Inferno extends kit {
    public Inferno(String name, ItemStack guiItem, ArrayList<String> loreAbility, Integer cooldown, ItemStack abilityItem) {
        super(name,guiItem,loreAbility,cooldown,abilityItem);
    }

    @Override
    public void giveInv(Player player, String name) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);
        giveBasicInv(name,player);
        data.currectKit = this;
        player.getInventory().setItem(8, loadItems.Inferno);
    }

    @Override
    public void activateAbility(Player player) {
        DataPlayer data = main.getInstance().getDataManager().getDataPlayer(player);

        if (data.currectKit == this) {
            double cooldown = getCooldown() - ((System.currentTimeMillis() - data.InfernoAbilityUse) / 1000);
            if (cooldown <= 0l) {
                player.sendMessage("&fActiving &b&l" + name + " &fability");
                data.InfernoAbilityUse = System.currentTimeMillis();

                ///Ability
                new BukkitRunnable() {
                    double times = 0;
                    public void run() {
                        times++;

                        for (Entity players : player.getWorld().getEntities()) {
                            if (players.getLocation().distance(player.getLocation()) < 10 && !players.getName().equals(player.getName())) {
                                Location location = player.getLocation().clone().toVector().add(new Vector(0,1.5,0)).toLocation(player.getWorld());
                                //TODO make go lighter
                                Location clonedFrom = location.clone();
                                Vector startVector = clonedFrom.toVector();
                                Vector targetVector = players.getLocation().toVector();
                                clonedFrom.setDirection(targetVector.subtract(startVector));
                                Vector direction = clonedFrom.getDirection().normalize();
                                double t = 0;
                                for (int i = 0; i < 30; i++) {

                                    t += Math.PI / 8;

                                    double x = direction.getX() * 1 * t;
                                    double y = direction.getY() * 1 * t + 1.5;
                                    double z = direction.getZ() * 1 * t;

                                    location.add(x, y, z);
                                    boolean breakLoop=false;
                                    
                                    //FastMath.spawnParticle(EnumParticle.FLAME, 0, (float) times, location, player);
                                    FastMath.spawnParticleWithRandom(EnumParticle.,0,(int) (times / 5),location,player,times / 300,times / 300,times / 300);
                                    for (Entity entity : location.getChunk().getEntities()) {
                                        if (entity.getName() != player.getName()) {
                                            if (entity.getLocation().distance(location) < 2.5) {
                                                //FastMath.spawnParticle(EnumParticle.FLAME, 0, 5, location, player);
                                                if (entity instanceof Player) {
                                                    Player player1 = (Player) entity;
                                                    player1.damage(times / 20);
                                                }

                                                breakLoop=true;
                                            }
                                        }
                                    }
                                    location.subtract(x, y, z);


                                    if (t >= 5 * Math.PI) {
                                        breakLoop = true;
                                    }

                                    if (breakLoop) {
                                        break;
                                    }
                                }
                            }
                        }

                        if (times >= 60) {
                            this.cancel();
                            return;
                        }
                    }
                }.runTaskTimer(main.getInstance(),0,1);
            }
            } else player.sendMessage("oncooldown " + cooldown);
        }
    }



