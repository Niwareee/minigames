package fr.niware.spleef.config;

import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.message.GameMessage;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

public class SpleefMessage extends GameMessage {

    public SpleefMessage(IGameBase<?, ?> gameBase) {
        super(gameBase);
    }

    @Override
    public Component getJoin(AbstractPlayer<?> abstractPlayer) {
        return this.getMessage("§b%s §ajoined the game. §e(%s/%s)", abstractPlayer.getName(), abstractPlayer.getArena().getPlayers().size(), super.gameBase.getMax());
    }

    @Override
    public Component getQuit(AbstractPlayer<?> abstractPlayer) {
        return this.getMessage("§b%s §aleft the game. §e(%s/%s)", abstractPlayer.getName(), abstractPlayer.getArena().getPlayers().size(), super.gameBase.getMax());
    }

    @Override
    public Component getStart(Arena<?> arena) {
        return Component.empty();
    }

    @Override
    public Component getEliminate(AbstractPlayer<?> abstractPlayer) {
        return this.getMessage("§c%s §6died. §f(%s/%s)", abstractPlayer.getName(), abstractPlayer.getArena().getPlayers().size(), abstractPlayer.getArena().getStartSize());
    }

    @Override
    public Title getTitleEliminate() {
        return Title.title(Component.text("§cYou died"), Component.text(""));
    }

    @Override
    public Component getFinish(String winners) {
        return this.getMessage("\n §7» §6End of fight\n\n§8§l• §a%s §ewon.\n", winners);
    }

    @Override
    public Title getWinner() {
        return Title.title(Component.text("§bCongratulations"), Component.text("§aYou won"));
    }
}
