package fr.niware.gamesapi.utils.gui;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public final class RInventoryTask extends BukkitRunnable {

    private final RInventoryManager rInventoryManager;

    public RInventoryTask(RInventoryManager rInventoryManager) {
        this.rInventoryManager = rInventoryManager;
    }

    @Override
    public void run() {
        this.rInventoryManager.get().forEach(rInventory -> {
            Map<UUID, Integer> runnableMap = this.rInventoryManager.get(rInventory).getRunnableMap();
            rInventory.getRunnableList().forEach(rInventoryRunnable -> {
                if (!runnableMap.containsKey(rInventoryRunnable.getUuid())) {
                    runnableMap.put(rInventoryRunnable.getUuid(), 0);
                }
                if (runnableMap.get(rInventoryRunnable.getUuid()) == rInventoryRunnable.getDelay()) {
                    rInventoryRunnable.getRunnable().run();
                    runnableMap.put(rInventoryRunnable.getUuid(), 0);
                }
                runnableMap.put(rInventoryRunnable.getUuid(), runnableMap.get(rInventoryRunnable.getUuid()) + 1);
            });
        });
    }
}
