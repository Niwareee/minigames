package fr.niware.zombie.world;

import fr.niware.gamesapi.world.GameWorld;
import fr.niware.zombie.ZombiePlugin;
import org.bukkit.GameRule;
import org.bukkit.Location;

public class ZombieWorld extends GameWorld {

    public ZombieWorld(ZombiePlugin plugin) {
        super(plugin);
        this.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
        this.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }

    @Override
    public String getWorldName() {
        return "world";
    }

    @Override
    public long getTime() {
        return 18000L;
    }

    @Override
    public boolean getPvP() {
        return false;
    }
}
