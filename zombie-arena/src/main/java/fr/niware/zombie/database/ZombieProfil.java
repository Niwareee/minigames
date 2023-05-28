package fr.niware.zombie.database;

import java.util.UUID;

public class ZombieProfil {

    private final UUID uuid;
    private final String name;

    private int kills;
    private long survivalTime;

    public ZombieProfil(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.kills = 0;
        this.survivalTime = 0;
    }

    public ZombieProfil(UUID uuid, String name, int kills, long survivalTime) {
        this.uuid = uuid;
        this.name = name;
        this.kills = kills;
        this.survivalTime = survivalTime;
    }

    public UUID getId() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKills(int kills) {
        this.kills = kills;
    }

    public long getSurvivalTime() {
        return this.survivalTime;
    }

    public void addSurvivalTime(long survivalTime) {
        this.survivalTime += survivalTime;
    }
}
