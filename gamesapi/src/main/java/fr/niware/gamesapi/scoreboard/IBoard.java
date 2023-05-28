package fr.niware.gamesapi.scoreboard;

import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.scoreboard.fastboard.FastBoard;

public interface IBoard<T extends AbstractPlayer<U>, U extends Arena<T>> {

    FastBoard getBoard();

    void delete();

    void init(FastBoard board);

    void update();
}
