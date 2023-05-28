package fr.niware.spleef.scoreboard.list;

import com.nftworlds.wallet.objects.NFTPlayer;
import com.nftworlds.wallet.objects.Network;
import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.spleef.SpleefGame;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.player.SpleefPlayer;

public class LobbyBoard extends AbstractBoard<SpleefGame, SpleefPlayer, SpleefArena> {

    public LobbyBoard(SpleefPlugin plugin, SpleefPlayer player) {
        super(plugin, player);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aSpleefVerse");
    }

    @Override
    public void update() {
        NFTPlayer nftPlayer = SpleefPlugin.getWallet().getNFTPlayer(super.abstractPlayer.getId());
        super.getBoard().updateLines(
                "",
                "§2§lPlayer Info",
                " §8» §f" + super.abstractPlayer.getPlayer().getPing() + " ms",
                " §8» §7" + nftPlayer.getPrimaryWallet().getWRLDBalance(Network.POLYGON) + " WRLD",
                "",
                "§a/top wins"
        );
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}