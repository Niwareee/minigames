package fr.niware.spleef.command;

import fr.niware.gamesapi.utils.registers.AbstractCommand;
import fr.niware.spleef.SpleefGame;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.player.SpleefPlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopCommand extends AbstractCommand<SpleefGame, SpleefPlayer, SpleefArena> {

    private static final String USAGE_MESSAGE = "§cUsage: /top <wins>.";

    private final SpleefDatabase spleefDatabase;

    public TopCommand(SpleefPlugin plugin) {
        super(plugin, "top");
        this.spleefDatabase = plugin.getSpleefDatabase();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(TopCommand.USAGE_MESSAGE);
            this.spleefDatabase.handleStop();
            return false;
        }

        if ("wins".equalsIgnoreCase(args[0])) {
            List<SpleefProfil> sortByWins = this.spleefDatabase.getTopPlayers().values().stream().sorted(Comparator.comparingInt(SpleefProfil::getWins).reversed()).toList();
            sender.sendMessage("Top wins:");
            for (int i = 0; i < 20; i++) {
                if (i >= sortByWins.size()) {
                    continue;
                }

                SpleefProfil spleefProfil = sortByWins.get(i);
                sender.sendMessage("§e" + (i + 1) + ". §d" + spleefProfil.getName() + "§e: §6" + spleefProfil.getWins() + " wins");
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
            return List.of("wins");
        }
        return Collections.emptyList();
    }
}
