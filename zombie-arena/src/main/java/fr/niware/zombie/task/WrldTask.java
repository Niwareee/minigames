package fr.niware.zombie.task;

import com.google.common.util.concurrent.AtomicDouble;
import com.nftworlds.wallet.objects.Network;
import fr.niware.gamesapi.utils.registers.AbstractTask;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.database.ZombieProfil;
import fr.niware.zombie.game.arena.ZombieArena;
import fr.niware.zombie.game.ZombiePlayer;

import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

public class WrldTask extends AbstractTask<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombiePlugin plugin;
    private final int wrldStartAward;

    private boolean hasPassed;

    public WrldTask(ZombiePlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.wrldStartAward = plugin.getGameBase().getGameFile().getConfig().getWrldStartAward();
    }

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        if (!this.hasPassed && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
            this.hasPassed = true;

            AtomicDouble start = new AtomicDouble(this.wrldStartAward + 50.0);
            Map<ZombieProfil, AtomicDouble> zombiesPlayers = this.plugin.getZombieDatabase().getTopPlayers().values().stream()
                    .collect(Collectors.toConcurrentMap(e -> e, e -> {
                        start.set(this.wrldStartAward * 0.05D);
                        return start;
                    }));

            for (Map.Entry<ZombieProfil, AtomicDouble> mapPlayer : zombiesPlayers.entrySet()) {
                ZombiePlugin.getWallet().sendWRLD(mapPlayer.getKey().getId(), mapPlayer.getValue().get(), Network.POLYGON, "Top Players of OneMap");
            }
        }
    }

    @Override
    public long getPeriod() {
        return 20 * 60 * 3600L;
    }
}
