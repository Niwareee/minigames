package fr.niware.gamesapi.commands;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.utils.registers.AbstractCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class TestCommand<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> extends AbstractCommand<T, U, V> {

    public TestCommand(AbstractPlugin<T, U, V> plugin) {
        super(plugin, "test");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }

        sender.sendActionBar(Component.text("§eTest command"));
        return true;
    }
}
