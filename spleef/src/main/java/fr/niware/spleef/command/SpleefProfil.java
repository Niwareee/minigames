package fr.niware.spleef.command;

import java.util.UUID;

public class SpleefProfil {

    private final UUID uuid;
    private final String name;

    private int wins;

    public SpleefProfil(UUID uuid, String name, int wins) {
        this.uuid = uuid;
        this.name = name;
        this.wins = wins;
    }

    public SpleefProfil(UUID uuid, String name) {
        this(uuid, name, 0);
    }

    public UUID getId() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public int getWins() {
        return this.wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void addWins() {
        this.wins++;
    }
}