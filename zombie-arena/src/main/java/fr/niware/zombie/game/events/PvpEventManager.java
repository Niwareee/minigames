package fr.niware.zombie.game.events;

import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PvpEventManager {

    private final ZombiePlugin plugin;
    private final ZombieArena arena;

    public PvpEventManager(ZombiePlugin plugin) {
        this.plugin = plugin;
        this.arena = plugin.getGameBase().getArenas().get(0);
    }

    public void handleJoin(Player player) {
        this.arena.teleport(player);

        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().addItem(new ItemStack(Material.BOW));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
    }

    public void handleStart() {
        this.arena.getSpawnLocation().getWorld().setPVP(true);
        this.plugin.getGameBase().getArenas().forEach(ZombieArena::forceReset);
        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            ZombiePlayer zombiePlayer = this.plugin.getGameBase().getPlayer(player.getUniqueId());
            zombiePlayer.setArena(this.arena);
            zombiePlayer.setScoreboard(this.plugin.getGameBoard().instanceLobbyBoard(zombiePlayer));
            this.handleJoin(player);
        });
    }

    public void handleStop() {
        this.arena.getSpawnLocation().getWorld().setPVP(false);
        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            this.arena.teleport(player);
            this.plugin.getGameBase().clearPlayer(player);
        });

        this.plugin.getGameWorld().getWorld().getEntities().stream().filter(entity -> entity.getType() == EntityType.DROPPED_ITEM).forEach(Entity::remove);
    }

    public Location getEventLocation() {
        return this.arena.getSpawnLocation();
    }
}
