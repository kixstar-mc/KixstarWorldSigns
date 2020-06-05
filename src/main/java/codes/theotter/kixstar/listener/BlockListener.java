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

import java.util.ArrayList;

public class BlockListener implements Listener {
    private final Material[] sign_materials = new Material[]{
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
    };

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

        if (!doPlayerHavePermissionToUseSign(sign_block, event.getPlayer()) && event.getPlayer().hasPermission("kixstarworlds.all")) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You don't have permission to use this sign."));
            return;
        }

        SafeTTeleporter teleporter = KixstarWorldSigns.getInstance().getMultiverseCore().getSafeTTeleporter();
        MVDestination destination = KixstarWorldSigns.getInstance().getMultiverseCore().getDestFactory().getDestination(sign_block.getLine(1).trim());
        teleporter.safelyTeleport(Bukkit.getConsoleSender(), event.getPlayer(), destination);
    }

    public boolean doPlayerHavePermissionToUseSign(Sign sign, Player player) {
        if (sign.getLine(2).trim().equalsIgnoreCase("")) {
            return true;
        }

        String configuration_path = "restricted_signs." + sign.getLine(2).trim();
        ArrayList<String> allowed_player_uuids = (ArrayList<String>) KixstarWorldSigns.getInstance().getConfig().getList(configuration_path, new ArrayList<String>());

        return allowed_player_uuids.contains(player.getUniqueId().toString());
    }

    public boolean isSignMaterial(Material material) {
        for (int index = 0; index < sign_materials.length; index++) {
            Material material_at_index = sign_materials[index];

            if (material_at_index == material) {
                return true;
            }
        }

        return false;
    }
}
