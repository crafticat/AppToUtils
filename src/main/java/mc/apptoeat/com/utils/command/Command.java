package mc.apptoeat.com.utils.command;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.core;
import mc.apptoeat.com.utils.objects.PlayerRunnable;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class Command extends BukkitCommand {

    private PlayerRunnable onCommand;
    private String arg;
    private String[] args;
    private Player player;

    public Command(String name, String description, String usageMessage, PlayerRunnable command) {
        super(name);
        this.description = description;
        this.usageMessage = usageMessage;
        this.setAliases(new ArrayList<>());
        this.onCommand = command;
        ((CraftServer) core.getInstance().getServer()).getCommandMap().register(name, this);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            this.arg = commandLabel;
            this.args = args;
            this.player = player;
            onCommand.run(player);
        }
        return false;
    }
}
