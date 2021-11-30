package mc.apptoeat.com.utils.packets.handle;

import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.data.DataLocation;
import mc.apptoeat.com.utils.data.DataPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.entity.Player;

public class FlyingPacket {

    public static void handle(Player p, PacketPlayInFlying wrapper) {
        try {
            DataLocation to = new DataLocation(wrapper.a(), wrapper.b(), wrapper.c(), wrapper.d(), wrapper.e(), p.getWorld(),wrapper.f());
            DataPlayer data = core.getInstance().getDataManager().getDataPlayer(p.getPlayer());
            DataLocation from;
            if (data.lastPosUpdate != null) {
                from = data.lastPosUpdate;
            } else from = to;
            data.lastPosUpdate = to;

            core.getInstance().getEventManager().getEvents().forEach(event -> {
                event.moveEvent(p,to,from,wrapper);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
