package fr.niware.zombie.listeners;

import fr.niware.gamesapi.events.ArenaPlayerJoinEvent;
import fr.niware.gamesapi.events.GameFinishEvent;
import fr.niware.gamesapi.events.GameStartEvent;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.player.ZombiePlayer;
import org.bukkit.event.EventHandler;

public class GameListener extends AbstractListener<ZombieGame, ZombiePlayer, ZombieArena> {

    public GameListener(ZombiePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onArenaJoin(ArenaPlayerJoinEvent<ZombiePlayer, ZombieArena> event) {
        event.getArena().spawnNPC(event.getPlayer());
    }

    @EventHandler
    public void onGameStart(GameStartEvent<ZombiePlayer, ZombieArena> event) {
        super.gameBase.nextRound(event.getArena(), true);
    }

    @EventHandler
    public void onGameFinish(GameFinishEvent<ZombiePlayer, ZombieArena> event) {
        super.gameBase.nextRound(event.getArena(), true);
    }
}
