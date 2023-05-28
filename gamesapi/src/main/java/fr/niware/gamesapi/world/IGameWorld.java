package fr.niware.gamesapi.world;

import org.bukkit.World;
import org.bukkit.scoreboard.Team;

public interface IGameWorld {

    World getWorld();

    String getWorldName();

    long getTime();

    boolean getPvP();

    Team getDefaultTeam();

    void removeEntities();
}
