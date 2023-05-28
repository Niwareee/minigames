package fr.niware.zombie;

import com.nftworlds.wallet.api.WalletAPI;
import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.config.IGameConfig;
import fr.niware.gamesapi.game.message.GameMessage;
import fr.niware.gamesapi.utils.registers.Registrable;
import fr.niware.gamesapi.world.IGameWorld;
import fr.niware.zombie.commands.AirdropCommand;
import fr.niware.zombie.commands.PvpCommand;
import fr.niware.zombie.commands.TopCommand;
import fr.niware.zombie.config.ZombieConfig;
import fr.niware.zombie.config.ZombieMessage;
import fr.niware.zombie.database.ZombieDatabase;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import fr.niware.zombie.game.events.PvpEventManager;
import fr.niware.zombie.game.scoreboard.ZombieBoard;
import fr.niware.zombie.listeners.GameListener;
import fr.niware.zombie.listeners.PvpEventListener;
import fr.niware.zombie.listeners.ZombieListener;
import fr.niware.zombie.task.ZombieTask;
import fr.niware.zombie.world.ZombieWorld;

public class ZombiePlugin extends AbstractPlugin<ZombieGame, ZombiePlayer, ZombieArena> {

    private static final WalletAPI wallet = new WalletAPI();

    private ZombieDatabase zombieDatabase;
    private PvpEventManager pvpEventManager;

    @Override
    public void enable(IGameConfig gameConfig) {
        this.zombieDatabase = new ZombieDatabase(this);
        this.pvpEventManager = new PvpEventManager(this);
    }

    @Override
    public void onDisable() {
        this.zombieDatabase.handleStop();
    }

    public static WalletAPI getWallet() {
        return ZombiePlugin.wallet;
    }

    @Override
    public boolean useDatabase() {
        return true;
    }

    @Override
    public Registrable[] getRegistreables() {
        return new Registrable[]{
                new TopCommand(this),
                new PvpCommand(this),
                new AirdropCommand(this),
                new GameListener(this),
                new ZombieListener(this),
                new PvpEventListener(this),
                new ZombieTask(this),
        };
    }

    @Override
    public IGameConfig instanceGameConfig() {
        return new ZombieConfig();
    }

    @Override
    public IGameWorld instanceGameWorld() {
        return new ZombieWorld(this);
    }

    @Override
    public ZombieGame instanceGameBase() {
        return new ZombieGame(this);
    }

    @Override
    public GameMessage instanceGameMessage() {
        return new ZombieMessage(this.getGameBase());
    }

    @Override
    public ZombieBoard instanceGameBoard() {
        return new ZombieBoard(this);
    }

    public ZombieDatabase getZombieDatabase() {
        return this.zombieDatabase;
    }

    public PvpEventManager getPvpEventManager() {
        return this.pvpEventManager;
    }
}
