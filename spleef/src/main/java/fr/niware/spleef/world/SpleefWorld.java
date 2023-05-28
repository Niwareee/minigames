package fr.niware.spleef.world;

import fr.niware.gamesapi.world.GameWorld;
import fr.niware.spleef.SpleefPlugin;
import org.bukkit.GameRule;

public class SpleefWorld extends GameWorld {

    public SpleefWorld(SpleefPlugin plugin) {
        super(plugin);
        this.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }

    @Override
    public String getWorldName() {
        return "world";
    }

    @Override
    public long getTime() {
        return 6000L;
    }

    @Override
    public boolean getPvP() {
        return false;
    }
}
