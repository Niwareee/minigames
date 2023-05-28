package fr.niware.zombie.task;

import fr.niware.gamesapi.utils.registers.AbstractTask;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import net.kyori.adventure.text.Component;

public class ZombieTask extends AbstractTask<ZombieGame, ZombiePlayer, ZombieArena> {

    public ZombieTask(ZombiePlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        super.gameBase.getArenas().forEach(zombieArena -> {
            if (zombieArena.getState().isGame()) {
                zombieArena.getPlayers().values().forEach(zombiePlayer -> {
                    zombiePlayer.addSurvivalTime(1);
                    zombiePlayer.getPlayer().sendActionBar(Component.text("§eRemaining zombies: §6" + zombieArena.getRound().getRemaining() + "/" + zombieArena.getRound().getInitCount()));
                });
                zombieArena.updateSortedKills();
                zombieArena.updateSortedTime();
            }
        });
    }

    @Override
    public long getPeriod() {
        return 20L;
    }
}
