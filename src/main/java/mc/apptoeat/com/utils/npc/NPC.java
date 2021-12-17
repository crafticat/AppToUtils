package mc.apptoeat.com.utils.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.npc.PathFinding.HandleEntity;
import mc.apptoeat.com.utils.npc.PathFinding.NPCInventory;
import mc.apptoeat.com.utils.npc.PathFinding.PathFinder;
import mc.apptoeat.com.utils.data.DataLocation;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.shortcuts.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

@Getter
@Setter
public class NPC extends Event {

    private EntityPlayer npc;
    private DataLocation location;
    private static DataLocation to;
    private Vector lastMotion = new Vector(0, 0, 0);
    private World world;
    private DataLocation oldLoc;
    private int ticks;
    private Player target;
    private Vector velocity;
    private boolean velocityTaken;
    private int smoothRot;
    private boolean reduce;
    private long lastYUpdate;
    private int attackTicks;
    private double reach;
    private boolean targeting;
    private boolean strafing;
    private int sideTicks;
    private boolean directionOfStrafe;
    private double health = 20;
    private double damage;
    private boolean alive;
    private NPCInventory inv;
    private Runnable entityDamageEvent;
    private Runnable moveEvent;
    private double hVelocity;
    private double vVelocity;
    private long hitDelay = 475;

    /* This Ai Utiles are made for pvp bots */
    /* Conations: Modify Able reach + cps, Abide Minecraft movements: Friction and generally how Minecraft calculates works , Smooth kb, The bot is able to reduce like a real player depends on your cps*/
    /* What's specials about it: it fully abides the Minecraft calculations which a lot of npc plugins don't such as citizens. */

    public NPC(UUID uuid,Location loc, String name, World w, Player target, int attackTicks, double reach, boolean targeting, boolean strafing, NPCInventory inventory, double h, double v) throws IOException {
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) w).getHandle();

        GameProfile profile = new GameProfile(UUID.randomUUID(), Color.code("&b&lAppToBot"));

            npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));

        URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
        InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());

        String var1 = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

        URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + var1 + "?unsigned=false");
        InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
        JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        String texture = textureProperty.get("value").getAsString();
        String signature = textureProperty.get("signature").getAsString();

        npc.getProfile().getProperties().put("textures", new Property("textures", texture, signature));

            npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            location = new DataLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            oldLoc = location;
            this.world = w;
            this.target = target;
            this.targeting = targeting;
            this.strafing = strafing;
            this.alive = true;
            this.inv = inventory;

            hVelocity = h;
            vVelocity = v;

            setItemsFromInv(inventory);
            sendCreatesPackets();

            core.getInstance().getEventManager().getEvents().add(this);
            if (targeting) StartTicking();
            core.getInstance().getNpcManager().getNpcs().add(this);

            velocity = new Vector(0,0,0);
            velocityTaken = false;
            this.attackTicks = attackTicks;
            this.reach = reach;
    }


    public void setItemsFromInv(NPCInventory inventory) {
        if (inventory.getMainItem() != null) npc.getBukkitEntity().setItemInHand(inventory.getMainItem());
        if (inventory.getHelmet() != null) npc.getBukkitEntity().getInventory().setHelmet(inventory.getHelmet());
        if (inventory.getChestPlace() != null) npc.getBukkitEntity().getInventory().setChestplate(inventory.getChestPlace());
        if (inventory.getLeggings() != null) npc.getBukkitEntity().getInventory().setLeggings(inventory.getLeggings());
        if (inventory.getBoots() != null) npc.getBukkitEntity().getInventory().setBoots(inventory.getBoots());
    }

    public void setPath(Location loc) {
        to = new DataLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch());
    }

    public void removeNPCPacket() {
        for (Player player : Bukkit.getOnlinePlayers()) Nms.sendPacket(player,new PacketPlayOutEntityDestroy(npc.getId()));
    }

    public void updatePos() {
        Vector lastMot = lastMotion.clone();

        if (velocityTaken) {
            velocityTaken = false;
        } else {
            velocity = new Vector(0,0,0);
        }

        float offSet = 0;
        if (nearEntitys() && strafing) {
            offSet = 45;
            if (directionOfStrafe) offSet *= -1;
            sideTicks++;

            if (sideTicks > 60) {
                directionOfStrafe = !directionOfStrafe;
                sideTicks = 0;
            }
        }

        double reduce = 1;
        if (this.reduce) reduce = 0.6;

        Vector motion = PathFinder.newMotion(location.clone(), lastMot, 0.1, world, npc.getId(), npc.onGround, velocity.clone(), offSet,reduce);
        lastMotion = motion;
        Vector newXYZ = checkForBlocksInWay(location, world, motion);
        location.setX(newXYZ.getX());
        location.setY(newXYZ.getY());
        location.setZ(newXYZ.getZ());

        if (moveEvent != null) {
            moveEvent.run();
        }
    }

    /**
     * Checks if the npc is near an entity.
     * @return boolean
     */
    public boolean nearEntitys() {
        for (Entity entity : location.toLocation(world).getChunk().getEntities()) {
            double reach = entity.getLocation().toVector().distance(location.toVector()) - 0.3;
            if (reach < 4) {
                if (entity instanceof Player) {
                    return true;
                }
            }
        }
        return false;
    }


    public static Vector checkForBlocksInWay(DataLocation from,World world,Vector motion) {
        Location checkForBlocks = from.toLocation(world).toVector().add(motion).toLocation(world);
        boolean locIsBlock = checkForBlocks.getBlock().getType() != org.bukkit.Material.AIR;
        Vector location = from.toLocation(world).toVector().add(motion);
        if (locIsBlock) {
            double requireYtoBeOnGround = checkForBlocks.getBlock().getY() + 1;
            location.setY(requireYtoBeOnGround);
        }

        return location;
    }

    public void checkForAttack() {
        /*ArrayList<Player> players = new ArrayList<>();
        for (Entity entity : location.toLocation(world).getChunk().getEntities()) {
            if (entity instanceof Player) {
                players.add((Player) entity);
            }
        }*/

        for (Entity entity : location.toLocation(world).getWorld().getEntities()) {
            double reach = entity.getLocation().toVector().distance(location.toVector()) - 0.3;
            if (reach < 5) {
                if (entity instanceof Player) {
                    attack(npc, (Player) entity);
                }
            }
            if (reach < this.reach) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    double damage = MathUtils.getItemDamageValue(inv.getMainItem(), player.getInventory().getHelmet(), player.getInventory().getChestplate(),player.getInventory().getLeggings(),player.getInventory().getBoots());
                    ((Damageable) entity).damage(damage, npc.getBukkitEntity());
                    reduce = true;
                }
            }
        }
    }

    public void attack(net.minecraft.server.v1_8_R3.Entity entity, Player player) {
        Nms.sendPacket(player, new PacketPlayOutAnimation(entity,0));
    }

    public NPC getThis() {
        return this;
    }

    public void StartTicking() {
        core.getInstance().getSyncTaskScheduler().addRepeatingTask(new Runnable() {
            @Override
            public void run() {
                if (alive) {
                    setPath(target.getLocation());
                    if (to != null) {

                        Location from = new Location(world, location.getX(), location.getY(), location.getZ());
                        Location t = new Location(world, to.getX(), to.getY(), to.getZ());

                        Vector rot = calculateYawDifference(from, t);

                        double deltaYaw = Math.abs(npc.yaw - rot.getX());
                        if (deltaYaw < 10) {
                            smoothRot++;
                        } else {
                            smoothRot = 0;
                        }

                        if (smoothRot < 5) location.setYaw((float) rot.getX());
                        location.setPitch((float) rot.getZ());

                        updatePos();
                        HandleEntity.check(location.toLocation(world),target,world,getThis());
                        ticks++;

                        reduce = false;

                        if (npc.locY != location.getY()) lastYUpdate = System.currentTimeMillis();

                        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            Nms.sendPacket(player, new PacketPlayOutEntityTeleport(npc));
                            Nms.sendPacket(player, new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
                            oldLoc = location;
                        }
                        if (ticks >= attackTicks) {
                            ticks = 0;
                            checkForAttack();
                        }
                    }
                }
            }
        },1);
    }

    public void kill() {
        alive = false;
        removeNPCPacket();
    }

    public static Vector calculateYawDifference(Location from, Location to) {
        Location clonedFrom = from.clone();
        Vector startVector = clonedFrom.toVector();
        Vector targetVector = to.toVector();
        clonedFrom.setDirection(targetVector.subtract(startVector));

        return new Vector(clonedFrom.getYaw(), 0, clonedFrom.getPitch());
    }

    private void sendCreatesPackets() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Nms.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            Nms.sendPacket(player, new PacketPlayOutNamedEntitySpawn(npc));
            Nms.sendPacket(player, new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));

            DataWatcher watcher = npc.getDataWatcher();
            watcher.watch(10, (byte) 127);
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(npc.getId(), watcher, true);
            Nms.sendPacket(player, packet);
        }
    }

    @Override
    public void joinEvent(Player player) {
        Nms.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        Nms.sendPacket(player, new PacketPlayOutNamedEntitySpawn(npc));
        Nms.sendPacket(player, new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
    }
}
