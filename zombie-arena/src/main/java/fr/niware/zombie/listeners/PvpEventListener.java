package fr.niware.zombie.listeners;

import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PvpEventListener extends AbstractListener<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombiePlugin plugin;

    public PvpEventListener(ZombiePlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(this.plugin.getPvpEventManager().getEventLocation());
        this.plugin.getPvpEventManager().handleJoin(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getWorld().getPVP()) {
            this.plugin.getPvpEventManager().handleJoin(event.getPlayer());
        }
    }
}
