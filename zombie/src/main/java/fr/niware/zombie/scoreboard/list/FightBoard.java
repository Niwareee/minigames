package fr.niware.zombie.scoreboard.list;

import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.player.ZombiePlayer;

public class FightBoard extends AbstractBoard<ZombieGame, ZombiePlayer, ZombieArena> {

    public FightBoard(ZombiePlugin plugin, ZombiePlayer player) {
        super(plugin, player);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aZombieVerse");
    }

    @Override
    public void update() {
        ZombieArena arena = super.abstractPlayer.getArena();
        super.getBoard().updateLines(
                "",
                " §7» §ePlayers: §6" + arena.getPlayers().size() + "/" + arena.getStartSize(),
                " §7» §eZombies: §6" + arena.getRound().getRemaining() + "/" + arena.getRound().getRoundEnum().getZombieCount(),
                " §7» " + arena.getRound().getRoundEnum().getName(),
                ""
        );
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}
