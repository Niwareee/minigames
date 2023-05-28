package fr.niware.gamesapi;

import fr.niware.gamesapi.database.SQLDatabase;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.config.IGameConfig;
import fr.niware.gamesapi.game.message.GameMessage;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.game.task.GameTask;
import fr.niware.gamesapi.listeners.DefaultListener;
import fr.niware.gamesapi.listeners.PlayerListener;
import fr.niware.gamesapi.scoreboard.GameBoard;
import fr.niware.gamesapi.utils.gui.RInventoryHandler;
import fr.niware.gamesapi.utils.packet.PacketManager;
import fr.niware.gamesapi.utils.registers.Registrable;
import fr.niware.gamesapi.world.IGameWorld;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractPlugin<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> extends JavaPlugin {

    private IGameConfig gameConfig;
    private IGameWorld gameWorld;
    private T gameBase;
    private GameMessage gameMessage;
    private GameBoard<T, U, V> gameBoard;

    private SQLDatabase sqlDatabase;
    private PacketManager packetManager;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        this.gameConfig = this.instanceGameConfig();
        this.gameWorld = this.instanceGameWorld();
        this.gameBase = this.instanceGameBase();
        this.gameMessage = this.instanceGameMessage();
        this.gameBoard = this.instanceGameBoard();

        if (this.useDatabase()) {
            this.sqlDatabase = new SQLDatabase(this);
        }
        this.packetManager = new PacketManager();

        this.enable(this.gameConfig);

        for (Registrable registrable : this.getRegistreables()) {
            registrable.register();
        }

        new DefaultListener<>(this).register();
        new PlayerListener<>(this).register();
        new GameTask<>(this).register();

        new RInventoryHandler(this);

        Runtime runtime = Runtime.getRuntime();
        this.getLog4JLogger().info("Running with {} threads and {} Mo.", runtime.availableProcessors(), runtime.maxMemory() / 1048576);
        this.getLog4JLogger().info("{} successfully enabled in {} ms", this.getName(), System.currentTimeMillis() - start);
    }

    @Override
    public void onDisable() {
        this.getLog4JLogger().info("{} plugin disabled !", this.getName());
    }

    public abstract void enable(IGameConfig gameConfig);

    public abstract boolean useDatabase();

    public abstract Registrable[] getRegistreables();

    public abstract IGameConfig instanceGameConfig();

    public abstract IGameWorld instanceGameWorld();

    public abstract T instanceGameBase();

    public abstract GameMessage instanceGameMessage();

    public abstract GameBoard<T, U, V> instanceGameBoard();

    public IGameConfig getGameConfig() {
        return this.gameConfig;
    }

    public IGameWorld getGameWorld() {
        return this.gameWorld;
    }

    public T getGameBase() {
        return this.gameBase;
    }

    public GameMessage getGameMessage() {
        return this.gameMessage;
    }

    public GameBoard<T, U, V> getGameBoard() {
        return this.gameBoard;
    }

    public SQLDatabase getSQLDatabase() {
        return this.sqlDatabase;
    }

    public PacketManager getPacketManager() {
        return this.packetManager;
    }
}
