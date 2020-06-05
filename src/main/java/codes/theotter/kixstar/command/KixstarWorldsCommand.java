package codes.theotter.kixstar.command;

import codes.theotter.kixstar.KixstarWorldSigns;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class KixstarWorldsCommand extends KixstarCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        String sub_command = args[0].trim().toLowerCase();

        if (sub_command.equalsIgnoreCase("help") || sub_command.equalsIgnoreCase("info")) {
            return false;
        }

        if (args.length < 3) {
            return false;
        }

        UUID playerId = Bukkit.getPlayerUniqueId(args[1]);
        if (playerId == null) {
            this.sendMessage(sender, "&4Can't find " + args[1] + ". Do they exist?");
            return true;
        }

        String sign_name = args[2].trim();
        String config_path = "restricted_signs." + sign_name;
        List<String> permitted_players = KixstarWorldSigns.getInstance().getConfig().getStringList(config_path);

        if (sub_command.equalsIgnoreCase("add")) {
            permitted_players.add(playerId.toString());
            this.sendMessage(sender, String.format("&aAdded %s to sign: %s", args[1], sign_name));
        } else if (sub_command.equalsIgnoreCase("remove")) {
            permitted_players.remove(playerId.toString());
            this.sendMessage(sender, String.format("&aRemoved %s to sign: %s", args[1], sign_name));

        } else {
            return false;
        }

        if (Bukkit.getPluginManager().getPermission(String.format("kixstarworlds.world.%s", sign_name)) == null) {
            Permission parentPermission = Bukkit.getPluginManager().getPermission("kixstarworlds.world.*");
            if (parentPermission == null) {
                sender.getServer().getLogger().fine("Cannot find kixstarworlds.world.*. Please contact the plugin developer.");
            } else {
                Permission permission = new Permission(String.format("kixstarworlds.world.%s", sign_name));
                Bukkit.getPluginManager().addPermission(permission);
                permission.addParent(parentPermission, true);
                sender.getServer().getLogger().fine(String.format("Registered world %s.", sign_name));
            }
        }

        KixstarWorldSigns.getInstance().getConfig().set(config_path, permitted_players);
        KixstarWorldSigns.getInstance().saveConfig();
        return true;

    }
}
