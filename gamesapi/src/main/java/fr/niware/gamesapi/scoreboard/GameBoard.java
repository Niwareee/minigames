package fr.niware.gamesapi.scoreboard;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;

public abstract class GameBoard<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> {

    protected AbstractPlugin<T, U, V> plugin;
    protected T gameBase;

    protected GameBoard(AbstractPlugin<T, U, V> plugin) {
        this.plugin = plugin;
        this.gameBase = plugin.getGameBase();

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> this.gameBase.getPlayers().forEach(gamePlayer -> gamePlayer.getScoreboard().update()), 0L, 15L);
    }

    public abstract AbstractBoard<T, U, V> instanceLobbyBoard(U abstractPlayer);

    public abstract AbstractBoard<T, U, V> instanceGameBoard(U abstractPlayer);
}
