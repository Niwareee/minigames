package fr.niware.gamesapi.events;

import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class GameStartEvent<T extends AbstractPlayer<U>, U extends Arena<T>> extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final U arena;
    private final Collection<T> players;

    public GameStartEvent(U arena) {
        this.arena = arena;
        this.players = arena.getPlayers().values();
    }

    public Collection<T> getPlayers() {
        return this.players;
    }

    public U getArena() {
        return this.arena;
    }

    @Override
    public HandlerList getHandlers() {
        return GameStartEvent.HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return GameStartEvent.HANDLERS_LIST;
    }
}
