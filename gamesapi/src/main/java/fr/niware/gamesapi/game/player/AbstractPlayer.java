package fr.niware.gamesapi.game.player;

import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.scoreboard.AbstractBoard;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class AbstractPlayer<T extends Arena<?>> implements Cloneable {

    private final Player player;

    private T arena;
    private AbstractBoard<?, ?, T> scoreboard;
    private Location lastLocation;

    protected AbstractPlayer(Player player, T arena) {
        this.player = player;
        this.arena = arena;
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getId() {
        return this.player.getUniqueId();
    }

    public String getName() {
        return this.player.getName();
    }

    public void sendTitle(Title title) {
        this.player.showTitle(title);
    }

    public T getArena() {
        return this.arena;
    }

    public void setArena(T arena) {
        this.arena = arena;
    }

    public AbstractBoard<?, ?, T> getScoreboard() {
        return this.scoreboard;
    }

    public void setScoreboard(AbstractBoard<?, ?, T> scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public abstract void reset();

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError();
        }
    }
}
