package fr.niware.zombie.commands;

import fr.niware.gamesapi.utils.registers.AbstractCommand;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.player.ZombiePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopCommand extends AbstractCommand<ZombieGame, ZombiePlayer, ZombieArena> {

    private static final String USAGE_MESSAGE = "§cUsage: /top <kills>.";

    private final ZombieDatabase gladiatorDatabase;

    public TopCommand(ZombiePlugin plugin) {
        super(plugin, "top");
        this.gladiatorDatabase = plugin.getZombieDatabase();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(TopCommand.USAGE_MESSAGE);
            this.gladiatorDatabase.handleStop();
            return false;
        }

        if ("kills".equalsIgnoreCase(args[0])) {
            List<ZombieProfil> sortByWins = this.gladiatorDatabase.getTopPlayers().values().stream().sorted(Comparator.comparingInt(ZombieProfil::getKills).reversed()).toList();
            sender.sendMessage("Top kills:");
            for (int i = 0; i < 20; i++) {
                if (i >= sortByWins.size()) {
                    continue;
                }

                ZombieProfil spleefProfil = sortByWins.get(i);
                sender.sendMessage("§e" + (i + 1) + ". §d" + spleefProfil.getName() + "§e: §6" + spleefProfil.getKills() + " wins");
            }
            return true;
        }

        sender.sendMessage(TopCommand.USAGE_MESSAGE);
        return false;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return List.of("kills");
        }
        return Collections.emptyList();
    }
}
