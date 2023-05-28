package fr.niware.gamesapi.scoreboard;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;

public abstract class AbstractBoard<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> implements IBoard<U, V> {

    private final FastBoard board;

    protected AbstractPlugin<T, U, V> plugin;
    protected U abstractPlayer;
    protected T gameBase;

    protected AbstractBoard(AbstractPlugin<T, U, V> plugin, U abstractPlayer) {
        this.plugin = plugin;
        this.abstractPlayer = abstractPlayer;
        this.gameBase = plugin.getGameBase();

        FastBoard localBoard = new FastBoard(abstractPlayer.getPlayer());
        this.init(localBoard);
        this.board = localBoard;
    }

    @Override
    public FastBoard getBoard() {
        return this.board;
    }

    @Override
    public void delete() {
        this.board.delete();
    }

    public abstract long getPeriod();
}
