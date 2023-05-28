package fr.niware.zombie.config;

import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.message.GameMessage;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

public class ZombieMessage extends GameMessage {

    public ZombieMessage(IGameBase<?, ?> gameBase) {
        super(gameBase);
    }

    @Override
    public Component getJoin(AbstractPlayer<?> abstractPlayer) {
        return super.getMessage("§b%s §ajoined the game. §e(%s/%s)", abstractPlayer.getName(), abstractPlayer.getArena().getPlayers().size(), super.gameBase.getMax());
    }

    @Override
    public Component getQuit(AbstractPlayer<?> abstractPlayer) {
        return super.getMessage("§b%s §aleft the game. §e(%s/%s)", abstractPlayer.getName(), abstractPlayer.getArena().getPlayers().size(), super.gameBase.getMax());
    }

    @Override
    public Component getStart(Arena<?> arena) {
        return Component.empty();
    }

    @Override
    public Component getEliminate(AbstractPlayer<?> abstractPlayer) {
        return super.getMessage("§c%s §6died. §f(%s/%s)", abstractPlayer.getName(), abstractPlayer.getArena().getPlayers().size(), abstractPlayer.getArena().getStartSize());
    }

    @Override
    public Title getTitleEliminate() {
        return Title.title(Component.text("§cYou died"), Component.text(""));
    }

    @Override
    public Component getFinish(String winners) {
        return this.getMessage("\n §7» §6End of fight\n\n§8§l• §a%s §ewon.\n", winners);
    }

    /* @Override
    public Component getFinish(List<String> players) {
        String[] array = new String[0];
        array[0] = "";
        array[1] = " §7» §6End of game";
        array[2] = "";
        array[4] = " §aTop survivors";
        int j = 4;
        for (int i = players.size() - 1; i > 0; i--) {
            array[j] = " §8§l" + (j - 3) + ". §e" + players.get(i);
            j++;
        }
        return super.getMessage(String.join("\n", array), players);
    }*/

    @Override
    public Title getWinner() {
        return Title.title(Component.text("§bCongratulations"), Component.text("§aYou won"));
    }
}
