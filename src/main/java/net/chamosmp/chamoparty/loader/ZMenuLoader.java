package net.chamosmp.chamoparty.loader;

import java.io.File;
import java.util.Optional;

import org.bukkit.entity.Player;

import fr.maxlego08.menu.api.Inventory;
import fr.maxlego08.menu.api.InventoryManager;
import fr.maxlego08.menu.api.exceptions.InventoryException;
import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.InventoryName;
import net.chamosmp.chamoparty.zcore.utils.ZUtils;

public class ZMenuLoader extends ZUtils {

	private InventoryManager inventoryManager;

	private final ZVotePartyPlugin plugin;

	/**
	 * @param plugin
	 */
	public ZMenuLoader(ZVotePartyPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	public void load() {
		this.inventoryManager = plugin.getProvider(InventoryManager.class);
	}

	public void reload() {
		if (this.inventoryManager == null) {
			this.plugin.getLogger().warning("Skipping inventory reload — zMenu is not available.");
			return;
		}

		File file = new File(this.plugin.getDataFolder(), "inventories/" + InventoryName.VOTE.getName() + ".yml");
		try {
			this.inventoryManager.deleteInventories(this.plugin);
			this.inventoryManager.loadInventory(this.plugin, file);
		} catch (InventoryException e) {
			e.printStackTrace();
		}
	}

	public void open(Player player) {
		Optional<Inventory> optional = this.inventoryManager.getInventory(InventoryName.VOTE.getName());
		if (optional.isPresent()) {
			Inventory inventory = optional.get();
			this.inventoryManager.openInventory(player, inventory);
		} else
			message(player, "§cErreur with inventory votes !");
	}

	public boolean isLoaded() {
		return this.inventoryManager != null;
	}

}
