package fr.niware.zombie.commands;

import java.util.UUID;

public class ZombieProfil {

    private final UUID uuid;
    private final String name;

    private int kills;

    public ZombieProfil(UUID uuid, String name, int kills) {
        this.uuid = uuid;
        this.name = name;
        this.kills = kills;
    }

    public ZombieProfil(UUID uuid, String name) {
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