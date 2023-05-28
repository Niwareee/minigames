package fr.niware.gamesapi.listeners;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.config.IGameConfig;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DefaultListener<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> extends AbstractListener<T, U, V> {

    private final IGameConfig gameConfig;

    public DefaultListener(AbstractPlugin<T, U, V> plugin) {
        super(plugin);
        this.gameConfig = plugin.getGameConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.getWorld().getPVP()) {
            return;
        }

        if (super.gameBase.isFighting(player.getUniqueId())) {
            event.setCancelled(!this.gameConfig.getDamageable()[1]);
            return;
        }

        event.setCancelled(!this.gameConfig.getDamageable()[0]);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(!this.gameConfig.isPlaceable());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(!this.gameConfig.isBreakable());
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().getPVP()) {
            return;
        }

        if (super.gameBase.isFighting(event.getEntity().getUniqueId())) {
            event.setCancelled(!this.gameConfig.getFoodable()[1]);
            return;
        }

        event.setCancelled(!this.gameConfig.getFoodable()[0]);
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (this.gameConfig.isCraftable()) {
            event.getInventory().setResult(null);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(EntityPickupItemEvent event) {
        if (event.getEntity().getWorld().getPVP()) {
            return;
        }
        event.setCancelled(!this.gameConfig.isPickupable());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getWorld().getPVP()) {
            return;
        }
        event.setCancelled(!this.gameConfig.isDropable());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getWorld().getPVP()) {
            return;
        }
        if (super.gameBase.isFighting(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(!this.gameConfig.getClickable()[1]);
            return;
        }

        event.setCancelled(!this.gameConfig.getClickable()[0]);
    }
}
