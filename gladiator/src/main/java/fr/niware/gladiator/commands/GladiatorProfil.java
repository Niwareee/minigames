package fr.niware.gladiator.commands;

import java.util.UUID;

public class GladiatorProfil {

    private final UUID uuid;
    private final String name;

    private int kills;

    public GladiatorProfil(UUID uuid, String name, int kills) {
        this.uuid = uuid;
        this.name = name;
        this.kills = kills;
    }

    public GladiatorProfil(UUID uuid, String name) {
        this(uuid, name, 0);
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

    public void addKills() {
        this.kills++;
    }
}