package fr.niware.gladiator.world;

import fr.niware.gamesapi.world.GameWorld;
import fr.niware.gladiator.GladiatorPlugin;
import org.bukkit.GameRule;

public class GladiatorWorld extends GameWorld {

    public GladiatorWorld(GladiatorPlugin plugin) {
        super(plugin);
        this.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "kill @e"), 20L);
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
        return true;
    }
}
