package fr.niware.zombie.world;

import fr.niware.gamesapi.world.GameWorld;
import fr.niware.zombie.ZombiePlugin;
import org.bukkit.GameRule;

public class ZombieWorld extends GameWorld {

    public ZombieWorld(ZombiePlugin plugin) {
        super(plugin);
        super.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
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
