package fr.niware.spleef.listeners;

import fr.niware.gamesapi.events.ArenaPlayerJoinEvent;
import fr.niware.gamesapi.events.GameFinishEvent;
import fr.niware.gamesapi.events.GameStartEvent;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.spleef.SpleefGame;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.command.SpleefDatabase;
import fr.niware.spleef.player.SpleefPlayer;
import org.bukkit.event.EventHandler;

public class GameListener extends AbstractListener<SpleefGame, SpleefPlayer, SpleefArena> {

    private final SpleefDatabase spleefDatabase;

    public GameListener(SpleefPlugin plugin) {
        super(plugin);
        this.spleefDatabase = plugin.getSpleefDatabase();
    }

    @EventHandler
    public void onArenaJoin(ArenaPlayerJoinEvent<SpleefPlayer, SpleefArena> event) {
        event.getArena().spawnNPC(event.getPlayer());
    }

    @EventHandler
    public void onGameStart(GameStartEvent<SpleefPlayer, SpleefArena> event) {
        event.getArena().generateArenaLocations();
    }

    @EventHandler
    public void onGameFinish(GameFinishEvent<SpleefPlayer, SpleefArena> event) {
        event.getWinners().forEach(this.spleefDatabase::addStats);
    }
}
