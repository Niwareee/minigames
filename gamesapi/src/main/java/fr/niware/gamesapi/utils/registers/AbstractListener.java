package fr.niware.gamesapi.utils.registers;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.world.IGameWorld;
import org.bukkit.event.Listener;

public abstract class AbstractListener<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> implements Registrable, Listener {

    protected final AbstractPlugin<T, U, V> plugin;
    protected final T gameBase;
    protected final IGameWorld gameWorld;

    protected AbstractListener(AbstractPlugin<T, U, V> plugin) {
        this.plugin = plugin;
        this.gameBase = plugin.getGameBase();
        this.gameWorld = plugin.getGameWorld();
    }

    @Override
    public void register() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }
}
