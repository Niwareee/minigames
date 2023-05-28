package fr.niware.zombie.commands;

import com.google.common.util.concurrent.AtomicDouble;
import com.nftworlds.wallet.objects.Network;
import fr.niware.gamesapi.utils.registers.AbstractCommand;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.database.ZombieDatabase;
import fr.niware.zombie.database.ZombieProfil;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.stream.Collectors;

public class AirdropCommand extends AbstractCommand<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombieDatabase zombieDatabase;

    public AirdropCommand(ZombiePlugin plugin) {
        super(plugin, "airdrop");
        this.zombieDatabase = plugin.getZombieDatabase();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender.isOp()) {
            return false;
        }

        AtomicDouble start = new AtomicDouble(this.plugin.getGameBase().getGameFile().getConfig().getWrldStartAward());
        Map<ZombieProfil, Double> zombiesPlayers = this.zombieDatabase.getTopPlayers().values().stream()
                .collect(Collectors.toConcurrentMap(e -> e, e -> start.getAndSet(start.get() - (start.get() * 0.05D))));

        zombiesPlayers.forEach((key, value) -> {
            this.plugin.getLog4JLogger().info("Send {} wrld to uuid={}", key.getName(), value);
            ZombiePlugin.getWallet().sendWRLD(key.getId(), value, Network.POLYGON, "Top Players of CryptoZombie");
        });
        return true;
    }
}
