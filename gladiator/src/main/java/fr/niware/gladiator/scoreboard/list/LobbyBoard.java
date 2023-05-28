package fr.niware.gladiator.scoreboard.list;

import com.nftworlds.wallet.objects.NFTPlayer;
import com.nftworlds.wallet.objects.Network;
import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.gladiator.GladiatorGame;
import fr.niware.gladiator.GladiatorPlugin;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.player.GladiatorPlayer;

public class LobbyBoard extends AbstractBoard<GladiatorGame, GladiatorPlayer, GladiatorArena> {

    public LobbyBoard(GladiatorPlugin plugin, GladiatorPlayer player) {
        super(plugin, player);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aGladiaVerse");
    }

    @Override
    public void update() {
        NFTPlayer nftPlayer = GladiatorPlugin.getWallet().getNFTPlayer(super.abstractPlayer.getId());
        super.getBoard().updateLines(
                "",
                "§2§lPlayer Info",
                " §8» §f" + super.abstractPlayer.getPlayer().getPing() + " ms",
                " §8» §7" + nftPlayer.getPrimaryWallet().getWRLDBalance(Network.POLYGON) + " WRLD",
                "",
                "§a/top kills"
        );
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}