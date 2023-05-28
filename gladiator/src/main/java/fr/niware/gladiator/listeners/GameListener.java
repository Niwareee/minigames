package fr.niware.gladiator.listeners;

import fr.niware.gamesapi.events.ArenaPlayerJoinEvent;
import fr.niware.gamesapi.events.GameFinishEvent;
import fr.niware.gamesapi.events.GamePlayerEliminateEvent;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.gladiator.GladiatorGame;
import fr.niware.gladiator.GladiatorPlugin;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.commands.GladiatorDatabase;
import fr.niware.gladiator.player.GladiatorPlayer;
import org.bukkit.event.EventHandler;

public class GameListener extends AbstractListener<GladiatorGame, GladiatorPlayer, GladiatorArena> {

    private final GladiatorDatabase gladiatorDatabase;

    public GameListener(GladiatorPlugin plugin) {
        super(plugin);
        this.gladiatorDatabase = plugin.getGladiatorDatabase();
    }

    @EventHandler
    public void onArenaJoin(ArenaPlayerJoinEvent<GladiatorPlayer, GladiatorArena> event) {
        event.getArena().spawnNPC(event.getPlayer());
    }

    @EventHandler
    public void onGamePlayerEliminate(GamePlayerEliminateEvent<GladiatorPlayer, GladiatorArena> event) {
        this.gladiatorDatabase.addStats(event.getGamePlayer());
    }

    @EventHandler
    public void onGameFinish(GameFinishEvent<GladiatorPlayer, GladiatorArena> event) {
        event.getArena().getPlayers().values().forEach(this.gladiatorDatabase::addStats);
    }
}

