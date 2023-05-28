package fr.niware.spleef.scoreboard.list;

import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.spleef.SpleefGame;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.player.SpleefPlayer;

public class FightBoard extends AbstractBoard<SpleefGame, SpleefPlayer, SpleefArena> {

    public FightBoard(SpleefPlugin plugin, SpleefPlayer player) {
        super(plugin, player);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aSpleefVerse");
    }

    @Override
    public void update() {
        SpleefArena arena = super.abstractPlayer.getArena();
        super.getBoard().updateLines(
                "",
                " §7» §ePlayers: §6" + arena.getPlayers().size() + "/" + arena.getStartSize(),
                " §7» §eBroken blocks: §6" + super.abstractPlayer.getBrokenBlocks(),
                ""
        );
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}
