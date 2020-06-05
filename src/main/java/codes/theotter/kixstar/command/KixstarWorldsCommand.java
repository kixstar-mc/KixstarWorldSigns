package codes.theotter.kixstar.command;

import codes.theotter.kixstar.KixstarWorldSigns;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KixstarWorldsCommand extends KixstarCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        String sub_command = args[0].trim().toLowerCase();

        if (sub_command.equalsIgnoreCase("help") || sub_command.equalsIgnoreCase("info")) {
            return false;
        }

        if (sub_command.equalsIgnoreCase("add")) {
            if (args.length < 3) {
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            String sign_name = args[2].trim();
            String config_path = "restricted_signs." + sign_name;

            if(player == null) {
                this.sendMessage(sender, "&4Can't find " + args[1] + ". Are they online?");
                return true;
            }

            List<String> permitted_players = (ArrayList<String>) KixstarWorldSigns.getInstance().getConfig().getList(config_path, new ArrayList<String>());
            permitted_players.add(player.getUniqueId().toString());
            KixstarWorldSigns.getInstance().getConfig().set(config_path, permitted_players);
            KixstarWorldSigns.getInstance().saveConfig();
            this.sendMessage(sender, "&aAdded " + player.getName() + " to sign: " + sign_name);

            return true;
        }

        if (sub_command.equalsIgnoreCase("remove")) {
            if (args.length < 3) {
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            String sign_name = args[2].trim();
            String config_path = "restricted_signs." + sign_name;
            List<String> permitted_players = (ArrayList<String>) KixstarWorldSigns.getInstance().getConfig().getList(config_path, new ArrayList<String>());
            permitted_players.remove(player.getUniqueId().toString());
            KixstarWorldSigns.getInstance().getConfig().set(config_path, permitted_players);
            KixstarWorldSigns.getInstance().saveConfig();
            this.sendMessage(sender, "&aRemoved " + player.getName() + " to sign: " + sign_name);

            return true;
        }

        return false;
    }
}
