package fr.niware.zombie.game.scoreboard.list;

import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;

import java.util.concurrent.atomic.AtomicInteger;

public class FightBoard extends AbstractBoard<ZombieGame, ZombiePlayer, ZombieArena> {

    public FightBoard(ZombiePlugin plugin, ZombiePlayer zombiePlayer) {
        super(plugin, zombiePlayer);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aCryptoZombieZ");
        board.updateLine(1, "§aTop kills:");
    }

    @Override
    public void update() {
        ZombieArena zombieArena = super.abstractPlayer.getArena();
        if (zombieArena.getStartSize() == 1) {
            super.getBoard().updateLines(
                    "",
                    " §7» §eKills: §6" + super.abstractPlayer.getKills(),
                    " §7» §eSurvival time: §6" + super.abstractPlayer.getSurvivalTimeString(),
                    ""
            );
            return;
        }

        AtomicInteger line = new AtomicInteger(2);
        zombieArena.getSortedPlayerKills().forEach(gamePlayer -> super.getBoard().updateLine(line.getAndIncrement(), " §7» §6" + gamePlayer.getName() + "§7: §f" + gamePlayer.getKills()));
        super.getBoard().updateLine(line.getAndIncrement(), " ");
        super.getBoard().updateLine(line.getAndIncrement(), "§aTop survival time:");
        zombieArena.getSortedPlayerTime().forEach(gamePlayer -> super.getBoard().updateLine(line.getAndIncrement(), " §7» §6" + gamePlayer.getName() + "§7: §f" + gamePlayer.getSurvivalTimeString()));

        for (int i = 0; i < 3; i++) {
            int j = line.getAndIncrement();
            if (super.getBoard().getLines().size() >= line.get()) {
                super.getBoard().removeLine(j);
            }
        }
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}
