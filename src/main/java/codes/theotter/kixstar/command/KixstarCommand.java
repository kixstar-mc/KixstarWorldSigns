package codes.theotter.kixstar.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class KixstarCommand {
    protected void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}