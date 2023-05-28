package fr.niware.spleef;

import com.nftworlds.wallet.api.WalletAPI;
import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.config.IGameConfig;
import fr.niware.gamesapi.game.message.GameMessage;
import fr.niware.gamesapi.utils.registers.Registrable;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.command.SpleefDatabase;
import fr.niware.spleef.command.TopCommand;
import fr.niware.spleef.config.SpleefConfig;
import fr.niware.spleef.config.SpleefMessage;
import fr.niware.spleef.listeners.GameListener;
import fr.niware.spleef.listeners.SpleefListener;
import fr.niware.spleef.player.SpleefPlayer;
import fr.niware.spleef.scoreboard.SpleefBoard;
import fr.niware.spleef.task.SpleefTask;
import fr.niware.spleef.world.SpleefWorld;

public class SpleefPlugin extends AbstractPlugin<SpleefGame, SpleefPlayer, SpleefArena> {

    private static final WalletAPI wallet = new WalletAPI();

    private SpleefDatabase spleefDatabase;

    @Override
    public void enable(IGameConfig gameConfig) {
        this.spleefDatabase = new SpleefDatabase(this);
    }

    @Override
    public void onDisable() {
        this.spleefDatabase.handleStop();
    }

    public static WalletAPI getWallet() {
        return SpleefPlugin.wallet;
    }

    @Override
    public boolean useDatabase() {
        return true;
    }

    @Override
    public Registrable[] getRegistreables() {
        return new Registrable[] {
                new TopCommand(this),
                new SpleefListener(this),
                new GameListener(this),
                new SpleefTask(this),
        };
    }

    @Override
    public IGameConfig instanceGameConfig() {
        return new SpleefConfig();
    }

    @Override
    public SpleefWorld instanceGameWorld() {
        return new SpleefWorld(this);
    }

    @Override
    public SpleefGame instanceGameBase() {
        return new SpleefGame(this);
    }

    @Override
    public GameMessage instanceGameMessage() {
        return new SpleefMessage(this.getGameBase());
    }

    @Override
    public SpleefBoard instanceGameBoard() {
        return new SpleefBoard(this);
    }

    public SpleefDatabase getSpleefDatabase() {
        return this.spleefDatabase;
    }
}
