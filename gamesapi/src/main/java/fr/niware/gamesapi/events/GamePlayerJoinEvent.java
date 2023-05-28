package fr.niware.gamesapi.events;

import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePlayerJoinEvent<T extends AbstractPlayer<?>> extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final T abstractPlayer;
    private final Arena<T> arena;

    public GamePlayerJoinEvent(T abstractPlayer, Arena<T> arena) {
        this.abstractPlayer = abstractPlayer;
        this.arena = arena;
    }

    public T getGamePlayer() {
        return this.abstractPlayer;
    }

    public Player getPlayer() {
        return this.abstractPlayer.getPlayer();
    }

    public Arena<T> getArena() {
        return this.arena;
    }

    @Override
    public HandlerList getHandlers() {
        return GamePlayerJoinEvent.HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return GamePlayerJoinEvent.HANDLERS_LIST;
    }
}
