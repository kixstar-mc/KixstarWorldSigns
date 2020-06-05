package codes.theotter.kixstar.listener;

import codes.theotter.kixstar.KixstarWorldSigns;
import com.onarandombox.MultiverseCore.api.MVDestination;
import com.onarandombox.MultiverseCore.api.SafeTTeleporter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class BlockListener implements Listener {
    private final List<Material> signMaterials = Arrays.asList(
            Material.SPRUCE_SIGN,
            Material.SPRUCE_WALL_SIGN,
            Material.ACACIA_SIGN,
            Material.ACACIA_WALL_SIGN,
            Material.BIRCH_SIGN,
            Material.BIRCH_SIGN,
            Material.DARK_OAK_SIGN,
            Material.DARK_OAK_WALL_SIGN,
            Material.JUNGLE_SIGN,
            Material.JUNGLE_WALL_SIGN,
            Material.OAK_SIGN,
            Material.OAK_WALL_SIGN
    );

    @EventHandler
    public void onBlockInteraction(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (!isSignMaterial(event.getClickedBlock().getType())) {
            return;
        }

        Sign sign_block = (Sign) event.getClickedBlock().getState();

        if (!sign_block.getLine(0).trim().equals("[KIX WORLDS]")) {
            return;
        }

        event.setCancelled(true);

        if (!canUseSign(sign_block, event.getPlayer())) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You don't have permission to use this sign."));
            return;
        }

        SafeTTeleporter teleporter = KixstarWorldSigns.getInstance().getMultiverseCore().getSafeTTeleporter();
        MVDestination destination = KixstarWorldSigns.getInstance().getMultiverseCore().getDestFactory().getDestination("w:" + sign_block.getLine(2).trim());
        teleporter.safelyTeleport(Bukkit.getConsoleSender(), event.getPlayer(), destination);

    }

    private boolean canUseSign(Sign sign, Player player) {
        String worldName = sign.getLine(2).trim();
        if (worldName.equals("")) {
            return false; // ? this should be false, there is no world set
        }

        // override permission for specific world, works with * since parent is added correctly
        if (player.hasPermission(String.format("kixstarworlds.world.%s", worldName))) {
            return true;
        }

        // path in config file
        List<String> allowedPlayerUUIDs = KixstarWorldSigns.getInstance()
                .getConfig()
                .getStringList(String.format("restricted_signs.%s", worldName));

        return allowedPlayerUUIDs.contains(player.getUniqueId().toString());

    }

    public boolean isSignMaterial(Material material) {
        return this.signMaterials.contains(material);
    }
}
