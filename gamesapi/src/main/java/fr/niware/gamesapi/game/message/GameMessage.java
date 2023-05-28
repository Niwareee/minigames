package fr.niware.gamesapi.game.message;

import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

public abstract class GameMessage {

    protected IGameBase<?, ?> gameBase;

    protected GameMessage(IGameBase<?, ?> gameBase) {
        this.gameBase = gameBase;
    }

    public Component getMessage(String message, Object... replaceBy) {
        return Component.text(String.format(message, replaceBy));
    }

    public abstract Component getJoin(AbstractPlayer<?> abstractPlayer);

    public abstract Component getQuit(AbstractPlayer<?> abstractPlayer);

    public abstract Component getStart(Arena<?> arena);

    public abstract Component getEliminate(AbstractPlayer<?> abstractPlayer);

    public abstract Title getTitleEliminate();

    public abstract Component getFinish(String winners);

    public abstract Title getWinner();
}
