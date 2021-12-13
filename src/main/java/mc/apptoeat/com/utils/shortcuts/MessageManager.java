package mc.apptoeat.com.utils.shortcuts;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class MessageManager {


    public static void sendMessage(Player player, String string) {
        player.sendMessage(Color.code(string));
    }

    public static void sendMessageWithCommand(Player player, String string,String command) {
        final TextComponent alertMessage = new TextComponent(Color.code(string));

        //Set the over and clicks events / messages.
        alertMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));

        player.spigot().sendMessage(alertMessage);
    }
}
