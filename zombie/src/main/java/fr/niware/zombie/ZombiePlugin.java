package fr.niware.zombie;

import com.nftworlds.wallet.api.WalletAPI;
import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.config.IGameConfig;
import fr.niware.gamesapi.game.message.GameMessage;
import fr.niware.gamesapi.utils.registers.Registrable;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.commands.ZombieDatabase;
import fr.niware.zombie.commands.TopCommand;
import fr.niware.zombie.config.ZombieConfig;
import fr.niware.zombie.config.ZombieMessage;
import fr.niware.zombie.listeners.GameListener;
import fr.niware.zombie.listeners.ZombieListener;
import fr.niware.zombie.player.ZombiePlayer;
import fr.niware.zombie.scoreboard.ZombieBoard;
import fr.niware.zombie.task.ZombieTask;
import fr.niware.zombie.world.ZombieWorld;

public class ZombiePlugin extends AbstractPlugin<ZombieGame, ZombiePlayer, ZombieArena> {

    private static final WalletAPI wallet = new WalletAPI();

    private ZombieDatabase zombieDatabase;

    @Override
    public void enable(IGameConfig gameConfig) {
        this.zombieDatabase = new ZombieDatabase(this);
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
                new GameListener(this),
                new ZombieListener(this),
                new ZombieTask(this),
        };
    }

    @Override
    public IGameConfig instanceGameConfig() {
        return new ZombieConfig();
    }

    @Override
    public ZombieWorld instanceGameWorld() {
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
}
