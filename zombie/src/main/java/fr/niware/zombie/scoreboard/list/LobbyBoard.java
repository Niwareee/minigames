package fr.niware.zombie.scoreboard.list;

import com.nftworlds.wallet.objects.NFTPlayer;
import com.nftworlds.wallet.objects.Network;
import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.player.ZombiePlayer;

public class LobbyBoard extends AbstractBoard<ZombieGame, ZombiePlayer, ZombieArena> {

    public LobbyBoard(ZombiePlugin plugin, ZombiePlayer player) {
        super(plugin, player);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aZombieVerse");
    }

    @Override
    public void update() {
        NFTPlayer nftPlayer = ZombiePlugin.getWallet().getNFTPlayer(super.abstractPlayer.getId());
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