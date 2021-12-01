package mc.apptoeat.com.utils.packets;

import io.netty.channel.*;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.packets.handle.FlyingPacket;
import mc.apptoeat.com.utils.packets.handle.JoinQuitPacket;
import mc.apptoeat.com.utils.packets.handle.RightClickPacket;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PacketsListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        removePlayer(e.getPlayer());
        core.getInstance().getDataManager().remove(e.getPlayer());
        JoinQuitPacket.quit(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        addPlayer(e.getPlayer());
        core.getInstance().getDataManager().add(e.getPlayer());
        JoinQuitPacket.join(e.getPlayer());
    }

    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        try {
            core.getInstance().getEventManager().getEvents().forEach(event -> {
                event.invClickEvent((Player) e.getWhoClicked(),e);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        RightClickPacket.handle(e.getPlayer(), e.getItem());
    }

    public void addPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            @Deprecated
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                if (packet instanceof PacketPlayInFlying) {
                    FlyingPacket.handle(player,(PacketPlayInFlying) packet);
                }

                super.channelRead(channelHandlerContext,packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                super.write(channelHandlerContext,packet,channelPromise);
            }
        };

        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        ChannelPipeline pipeline = channel.pipeline();
        if (pipeline == null)
            return;
        String handlerName = "atg_packet_processor";
        channel.eventLoop().submit(() -> {
            if(pipeline.get(handlerName) != null)
                pipeline.remove(handlerName);
            pipeline.addBefore("packet_handler", handlerName, channelDuplexHandler);
            return null;
        });
    }

    public void removePlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        ChannelPipeline pipeline = channel.pipeline();
        String handlerName = "hawk_packet_processor";
        channel.eventLoop().submit(() -> {
            if(pipeline.get(handlerName) != null)
                pipeline.remove(handlerName);
            return null;
        });
    }
}

