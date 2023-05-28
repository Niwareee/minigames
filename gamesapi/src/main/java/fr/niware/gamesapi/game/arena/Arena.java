package fr.niware.gamesapi.game.arena;

import fr.niware.gamesapi.game.player.AbstractPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Arena<T extends AbstractPlayer<?>> {

    private final int id;
    private final int maxSlots;
    private final Map<UUID, T> players = new HashMap<>();
    private final List<T> playersQueue = new LinkedList<>();

    private int timer;
    private int startSize;
    private ArenaState state = ArenaState.WAITING;

    protected Location[] locations;

    protected Arena(int id, int maxSlots) {
        this.id = id;
        this.maxSlots = maxSlots;
        this.resetTimer();
    }

    public int getId() {
        return this.id;
    }

    public int getMaxSlots() {
        return this.maxSlots;
    }

    public boolean canJoin() {
        return this.players.size() < this.maxSlots;
    }

    public Map<UUID, T> getPlayers() {
        return this.players;
    }

    public T getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    public void putPlayer(T abstractPlayer) {
        this.players.put(abstractPlayer.getId(), abstractPlayer);
    }

    public T getLastPlayer() {
        return (T) this.players.values().toArray()[0];
    }

    public boolean containsPlayer(UUID uuid) {
        return this.players.containsKey(uuid);
    }

    public T removePlayer(UUID uuid) {
        return this.players.remove(uuid);
    }

    public List<T> getPlayersQueue() {
        return this.playersQueue;
    }

    public int getPosition(T abstractPlayer) {
        return this.playersQueue.indexOf(abstractPlayer) + 1;
    }

    public boolean isQueueEmpty() {
        return this.playersQueue.isEmpty();
    }

    public Location[] getLocations() {
        return this.locations;
    }

    public ArenaState getState() {
        return this.state;
    }

    public boolean isFighting() {
        return this.state == ArenaState.FIGHTING;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public Location getSpawnLocation() {
        return this.locations[0];
    }

    public int getTimer() {
        return this.timer;
    }

    public void decrement() {
        this.timer--;
    }

    public void resetTimer() {
        this.timer = 21;
    }

    public int getStartSize() {
        return this.startSize;
    }

    public void setStartSize() {
        this.startSize = this.players.size();
    }

    public void teleport(Player player, int index) {
        player.teleport(this.getArenaLocations()[index]);
    }

    public void reset() {
        this.startSize = 0;
        this.players.clear();
    }

    protected abstract Location[] getArenaLocations();

    public abstract void spawnNPC(Player player);

    public abstract boolean isEnd();
}
