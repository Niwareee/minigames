package fr.niware.zombie.commands;

import fr.niware.gamesapi.utils.registers.AbstractCommand;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvpCommand extends AbstractCommand<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombiePlugin plugin;

    public PvpCommand(ZombiePlugin plugin) {
        super(plugin, "pvp");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!player.getWorld().getPVP()) {
            this.plugin.getPvpEventManager().handleStart();
            sender.sendMessage("§6You have set pvp to §atrue§6.");
            return true;
        }

        this.plugin.getPvpEventManager().handleStop();
        sender.sendMessage("§6You have set pvp to §cfalse§6.");
        return true;
    }
}
