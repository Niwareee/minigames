package fr.niware.gamesapi.events;

import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class GameFinishEvent<T extends AbstractPlayer<U>, U extends Arena<T>> extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Collection<T> winners;
    private final U arena;

    public GameFinishEvent(U arena) {
        this.winners = arena.getPlayers().values();
        this.arena = arena;
    }

    public Collection<T> getWinners() {
        return this.winners;
    }

    public U getArena() {
        return this.arena;
    }

    @Override
    public HandlerList getHandlers() {
        return GameFinishEvent.HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return GameFinishEvent.HANDLERS_LIST;
    }
}

