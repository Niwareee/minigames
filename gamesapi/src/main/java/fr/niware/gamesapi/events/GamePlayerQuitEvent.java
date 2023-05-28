package fr.niware.gamesapi.events;

import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePlayerQuitEvent<T extends AbstractPlayer<U>, U extends Arena<T>> extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final T abstractPlayer;
    private final U arena;

    public GamePlayerQuitEvent(T abstractPlayer) {
        this.abstractPlayer = abstractPlayer;
        this.arena = abstractPlayer.getArena();
    }

    public T getGamePlayer() {
        return this.abstractPlayer;
    }

    public Player getPlayer() {
        return this.abstractPlayer.getPlayer();
    }

    public U getArena() {
        return this.arena;
    }

    @Override
    public HandlerList getHandlers() {
        return GamePlayerQuitEvent.HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return GamePlayerQuitEvent.HANDLERS_LIST;
    }
}
