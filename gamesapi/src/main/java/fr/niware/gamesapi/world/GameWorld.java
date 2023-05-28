package fr.niware.gamesapi.world;

import fr.niware.gamesapi.AbstractPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scoreboard.Team;

public abstract class GameWorld implements IGameWorld {

    private final World world;

    private Team defaultTeam;

    protected GameWorld(AbstractPlugin<?, ?, ?> plugin) {
        this.world = plugin.getServer().getWorld(this.getWorldName());
        if (this.world == null) {
            return;
        }

        this.world.setPVP(this.getPvP());
        this.world.setFullTime(this.getTime());
        this.world.setDifficulty(Difficulty.NORMAL);

        this.world.setGameRule(GameRule.DO_FIRE_TICK, false);
        this.world.setGameRule(GameRule.DO_MOB_LOOT, false);
        this.world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        this.world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        this.world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        this.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        this.world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        this.world.setGameRule(GameRule.SPAWN_RADIUS, 0);

        plugin.getServer().setSpawnRadius(0);
        plugin.getServer().setDefaultGameMode(GameMode.ADVENTURE);
        plugin.getServer().getScheduler().runTaskLater(plugin, this::removeEntities, 40L);

        if (plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam("defaultTeam") == null) {
            this.defaultTeam = plugin.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("defaultTeam");
            this.defaultTeam.prefix(Component.text("Human "));
            this.defaultTeam.color(NamedTextColor.YELLOW);
            return;
        }

        this.defaultTeam = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam("defaultTeam");
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public Team getDefaultTeam() {
        return this.defaultTeam;
    }

    @Override
    public void removeEntities() {
        this.world.getEntities()
                .stream()
                .filter(entity -> entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND)
                .forEach(Entity::remove);
    }
}
