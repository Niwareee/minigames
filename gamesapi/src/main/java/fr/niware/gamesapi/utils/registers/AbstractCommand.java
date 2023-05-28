package fr.niware.gamesapi.utils.registers;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import org.bukkit.command.Command;

public abstract class AbstractCommand<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> extends Command implements Registrable {

    protected final AbstractPlugin<T, U, V> plugin;
    protected final T gameBase;

    protected AbstractCommand(AbstractPlugin<T, U, V> plugin, String name) {
        super(name);
        this.plugin = plugin;
        this.gameBase = plugin.getGameBase();
    }

    @Override
    public void register() {
        this.plugin.getServer().getCommandMap().register(this.plugin.getDescription().getName(), this);
    }
}
