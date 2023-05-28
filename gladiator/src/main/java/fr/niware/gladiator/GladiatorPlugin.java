package fr.niware.gladiator;

import com.nftworlds.wallet.api.WalletAPI;
import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.config.IGameConfig;
import fr.niware.gamesapi.game.message.GameMessage;
import fr.niware.gamesapi.utils.registers.Registrable;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.commands.GladiatorDatabase;
import fr.niware.gladiator.commands.TopCommand;
import fr.niware.gladiator.config.GladiatorConfig;
import fr.niware.gladiator.config.GladiatorMessage;
import fr.niware.gladiator.listeners.GameListener;
import fr.niware.gladiator.listeners.GladiatorListener;
import fr.niware.gladiator.player.GladiatorPlayer;
import fr.niware.gladiator.scoreboard.GladiatorBoard;
import fr.niware.gladiator.world.GladiatorWorld;

public class GladiatorPlugin extends AbstractPlugin<GladiatorGame, GladiatorPlayer, GladiatorArena> {

    private static final WalletAPI wallet = new WalletAPI();

    private GladiatorDatabase gladiatorDatabase;

    @Override
    public void enable(IGameConfig gameConfig) {
        this.gladiatorDatabase = new GladiatorDatabase(this);
    }

    @Override
    public void onDisable() {
        this.gladiatorDatabase.handleStop();
    }

    public static WalletAPI getWallet() {
        return GladiatorPlugin.wallet;
    }

    @Override
    public boolean useDatabase() {
        return true;
    }

    @Override
    public Registrable[] getRegistreables() {
        return new Registrable[] {
                new TopCommand(this),
                new GladiatorListener(this),
                new GameListener(this),
        };
    }

    @Override
    public IGameConfig instanceGameConfig() {
        return new GladiatorConfig();
    }

    @Override
    public GladiatorWorld instanceGameWorld() {
        return new GladiatorWorld(this);
    }

    @Override
    public GladiatorGame instanceGameBase() {
        return new GladiatorGame(this);
    }

    @Override
    public GameMessage instanceGameMessage() {
        return new GladiatorMessage(this.getGameBase());
    }

    @Override
    public GladiatorBoard instanceGameBoard() {
        return new GladiatorBoard(this);
    }

    public GladiatorDatabase getGladiatorDatabase() {
        return this.gladiatorDatabase;
    }
}
