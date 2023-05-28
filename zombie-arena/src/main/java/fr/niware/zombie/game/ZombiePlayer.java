package fr.niware.zombie.game;

import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ZombiePlayer extends AbstractPlayer<ZombieArena> {

    private final UUID uuid;
    private final String name;

    private int kills;
    private int survivalTime;

    public ZombiePlayer(Player player, ZombieArena zombieArena) {
        super(player, zombieArena);
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.kills = 0;
        this.survivalTime = 0;
    }

    @Override
    public UUID getId() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getKills() {
        return this.kills;
    }

    public void addKills(int kills) {
        this.kills = kills;
    }

    public void addKill() {
        this.kills++;
    }

    public int getSurvivalTime() {
        return this.survivalTime;
    }

    public String getSurvivalTimeString() {
        return String.format("%02d:%02d", this.survivalTime / 60, this.survivalTime % 60);
    }

    public void addSurvivalTime(int survivalTime) {
        this.survivalTime += survivalTime;
    }

    @Override
    public void reset() {
        this.kills = 0;
        this.survivalTime = 0;
    }
}
