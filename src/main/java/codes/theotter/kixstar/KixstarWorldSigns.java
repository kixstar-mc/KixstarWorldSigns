package codes.theotter.kixstar;

import codes.theotter.kixstar.command.KixstarWorldsCommand;
import codes.theotter.kixstar.listener.BlockListener;
import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class KixstarWorldSigns extends JavaPlugin {
    private MultiverseCore multiverse_core;
    private FileConfiguration configuration = this.getConfig();

    public void onEnable() {
        this.configuration.options().copyDefaults(true);
        saveConfig();
        Plugin multiverse_plugin = this.getServer().getPluginManager().getPlugin("Multiverse-Core");

        if (multiverse_plugin == null) {
            this.getLogger().severe("Multiverse Core Missing...");
            this.getServer().getPluginManager().disablePlugin(this);

            return;
        }

        this.multiverse_core = (MultiverseCore) multiverse_plugin;
        this.getServer().getPluginManager().registerEvents(new BlockListener(), this);
        this.getConfig().getList("", new ArrayList<String>());
        this.getCommand("kixstarworlds").setExecutor(new KixstarWorldsCommand());
    }

    public void onDisable() {

    }

    public static KixstarWorldSigns getInstance() {
        return (KixstarWorldSigns) Bukkit.getServer().getPluginManager().getPlugin("KixstarWorldSigns");
    }

    public MultiverseCore getMultiverseCore() {
        return this.multiverse_core;
    }
}
