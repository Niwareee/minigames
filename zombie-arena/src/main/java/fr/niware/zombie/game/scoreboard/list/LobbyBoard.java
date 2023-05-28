package fr.niware.zombie.game.scoreboard.list;

import com.nftworlds.wallet.objects.NFTPlayer;
import com.nftworlds.wallet.objects.Network;
import fr.niware.gamesapi.scoreboard.AbstractBoard;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;

public class LobbyBoard extends AbstractBoard<ZombieGame, ZombiePlayer, ZombieArena> {

    public LobbyBoard(ZombiePlugin plugin, ZombiePlayer zombiePlayer) {
        super(plugin, zombiePlayer);
    }

    @Override
    public void init(FastBoard board) {
        board.updateTitle("§aCryptoZombiez");
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
                "§a/top kills",
                "§a/top survivaltime"
        );
    }

    @Override
    public long getPeriod() {
        return 10L;
    }
}