package fr.niware.gamesapi.utils.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RInventoryHandler implements Listener {

    private final RInventoryManager inventoryManager;

    public RInventoryHandler(JavaPlugin plugin) {
        this.inventoryManager = new RInventoryManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        // new RInventoryTask(this.inventoryManager).runTaskTimerAsynchronously(plugin, 0L, 2L);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() instanceof RInventory<?> rInventory) {
            int slot = event.getRawSlot();
            event.setCancelled(true);
            if (rInventory.getSharedMap().containsKey(slot)) {
                rInventory.getSharedMap().get(slot).accept(event);
            }
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof RInventory<?> rInventory) {
            rInventory.getRunnableList().forEach(runnable -> runnable.getRunnable().run());
            this.inventoryManager.put(rInventory);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof RInventory<?> rInventory) {
            this.inventoryManager.remove(rInventory);
        }
    }
}
