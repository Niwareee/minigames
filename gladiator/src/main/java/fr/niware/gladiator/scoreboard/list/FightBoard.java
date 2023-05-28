package fr.niware.gladiator.scoreboard.list;

import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.gladiator.GladiatorGame;
import fr.niware.gladiator.GladiatorPlugin;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.player.GladiatorPlayer;

public class FightBoard extends AbstractBoard<GladiatorGame, GladiatorPlayer, GladiatorArena> {

    public FightBoard(GladiatorPlugin plugin, GladiatorPlayer player) {
        super(plugin, player);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aGladiaVerse");
    }

    @Override
    public void update() {
        GladiatorArena arena = super.abstractPlayer.getArena();
        super.getBoard().updateLines(
                "",
                " §7» §ePlayers: §6" + arena.getPlayers().size() + "/" + arena.getStartSize(),
                " §7» §eDamage: §c" + String.format("%.1f", super.abstractPlayer.getDamage()) + " ❤",
                ""
        );
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}
