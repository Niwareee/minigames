package fr.niware.zombie.listeners;

import fr.niware.gamesapi.events.ArenaPlayerJoinEvent;
import fr.niware.gamesapi.events.GameFinishEvent;
import fr.niware.gamesapi.events.GamePlayerEliminateEvent;
import fr.niware.gamesapi.events.GameStartEvent;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.database.ZombieDatabase;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import org.bukkit.event.EventHandler;

public class GameListener extends AbstractListener<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombieDatabase zombieDatabase;

    public GameListener(ZombiePlugin plugin) {
        super(plugin);
        this.zombieDatabase = plugin.getZombieDatabase();
    }

    @EventHandler
    public void onArenaJoin(ArenaPlayerJoinEvent<ZombiePlayer, ZombieArena> event) {
        event.getArena().spawnNPC(event.getPlayer());
    }

    @EventHandler
    public void onGameStart(GameStartEvent<ZombiePlayer, ZombieArena> event) {
        super.plugin.getServer().getScheduler().runTaskLater(super.plugin, () -> {
            event.getArena().spawnChest();
            super.gameBase.nextRound(event.getArena(), true);
        }, 10L);
    }

    @EventHandler
    public void onGamePlayerEliminate(GamePlayerEliminateEvent<ZombiePlayer, ZombieArena> event) {
        this.zombieDatabase.addStats(event.getGamePlayer());
        event.getArena().getCachePlayers().add(event.getGamePlayer().getName());
    }

    @EventHandler
    public void onGameFinish(GameFinishEvent<ZombiePlayer, ZombieArena> event) {
        ZombieArena arena = event.getArena();
        arena.getPlayers().values().forEach(zombiePlayer -> {
            this.zombieDatabase.addStats(zombiePlayer);
            arena.getCachePlayers().add(zombiePlayer.getName());
        });
        arena.reset();

        // this.plugin.getServer().broadcast(this.plugin.getGameMessage().getFinish(arena.getCachePlayers()));
    }
}
