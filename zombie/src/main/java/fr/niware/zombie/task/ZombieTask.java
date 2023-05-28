package fr.niware.zombie.task;

import fr.niware.gamesapi.utils.registers.AbstractTask;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.player.ZombiePlayer;
import net.kyori.adventure.text.Component;

public class ZombieTask extends AbstractTask<ZombieGame, ZombiePlayer, ZombieArena> {

    public ZombieTask(ZombiePlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        super.gameBase.getArenas().forEach(arena -> {
            if(arena.isFighting()) {
                arena.getPlayers().values().forEach(player -> player.getPlayer().sendActionBar(Component.text("§eRemaining zombie(s): §6" + arena.getRound().getRemaining() + "/" + arena.getRound().getZombieCount())));
            }
        });
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}
