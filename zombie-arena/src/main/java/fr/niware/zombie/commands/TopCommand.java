package fr.niware.zombie.commands;

import fr.niware.gamesapi.utils.registers.AbstractCommand;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.database.ZombieDatabase;
import fr.niware.zombie.database.ZombieProfil;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopCommand extends AbstractCommand<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombieDatabase zombieDatabase;

    public TopCommand(ZombiePlugin plugin) {
        super(plugin, "top");
        this.zombieDatabase = plugin.getZombieDatabase();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /top <kills/survivaltime>.");
            this.zombieDatabase.handleStop();
            return false;
        }

        if ("kills".equalsIgnoreCase(args[0])) {
            List<ZombieProfil> sortByKills = this.zombieDatabase.getTopPlayers().values().stream().sorted(Comparator.comparingInt(ZombieProfil::getKills).reversed()).toList();
            sender.sendMessage("Top kills:");
            for (int i = 0; i < 20; i++) {
                if (i >= sortByKills.size()) {
                    continue;
                }

                ZombieProfil zombiePlayer = sortByKills.get(i);
                sender.sendMessage("§e" + (i + 1) + ". §d" + zombiePlayer.getName() + "§e: §6" + zombiePlayer.getKills() + " kills");
            }
            return true;
        }

        if ("survivaltime".equalsIgnoreCase(args[0])) {
            List<ZombieProfil> sortBySurvivalTime = this.zombieDatabase.getTopPlayers().values().stream().sorted(Comparator.comparingLong(ZombieProfil::getSurvivalTime).reversed()).toList();
            sender.sendMessage("Top survival time:");
            for (int i = 0; i < 20; i++) {
                if (i >= sortBySurvivalTime.size()) {
                    continue;
                }

                ZombieProfil zombiePlayer = sortBySurvivalTime.get(i);
                sender.sendMessage("§e" + (i + 1) + ". §d" + zombiePlayer.getName() + "§e: §6" + zombiePlayer.getSurvivalTime() + "s");
            }
            return true;
        }

        sender.sendMessage("§cUsage: /top <kills/survivaltime>.");
        return false;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return Arrays.asList("kills", "survivaltime");
        }
        return Collections.emptyList();
    }
}
