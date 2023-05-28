package fr.niware.gladiator.commands;

import fr.niware.gamesapi.utils.registers.AbstractCommand;
import fr.niware.gladiator.GladiatorGame;
import fr.niware.gladiator.GladiatorPlugin;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.player.GladiatorPlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopCommand extends AbstractCommand<GladiatorGame, GladiatorPlayer, GladiatorArena> {

    private static final String USAGE_MESSAGE = "§cUsage: /top <kills>.";

    private final GladiatorDatabase gladiatorDatabase;

    public TopCommand(GladiatorPlugin plugin) {
        super(plugin, "top");
        this.gladiatorDatabase = plugin.getGladiatorDatabase();
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
            List<GladiatorProfil> sortByWins = this.gladiatorDatabase.getTopPlayers().values().stream().sorted(Comparator.comparingInt(GladiatorProfil::getKills).reversed()).toList();
            sender.sendMessage("Top kills:");
            for (int i = 0; i < 20; i++) {
                if (i >= sortByWins.size()) {
                    continue;
                }

                GladiatorProfil spleefProfil = sortByWins.get(i);
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
