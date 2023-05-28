package fr.niware.gamesapi.utils.registers;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.world.IGameWorld;
import org.bukkit.scheduler.BukkitTask;

public abstract class AbstractTask<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> implements Runnable, Registrable {

    protected final AbstractPlugin<T, U, V> plugin;
    protected final T gameBase;
    protected final IGameWorld gameWorld;

    private BukkitTask task;

    protected AbstractTask(AbstractPlugin<T, U, V> plugin) {
        this.plugin = plugin;
        this.gameBase = plugin.getGameBase();
        this.gameWorld = plugin.getGameWorld();
    }

    public BukkitTask getTask() {
        return this.task;
    }

    public abstract long getPeriod();

    @Override
    public void register() {
        this.task = this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this, 0L, this.getPeriod());
    }
}
