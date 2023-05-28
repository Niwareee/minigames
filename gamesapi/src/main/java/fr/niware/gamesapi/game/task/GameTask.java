package fr.niware.gamesapi.game.task;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.events.GameStartEvent;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.arena.ArenaState;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.scoreboard.GameBoard;
import fr.niware.gamesapi.utils.registers.AbstractTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class GameTask<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> extends AbstractTask<T, U, V> {

    private final GameBoard<T, U, V> gameBoard;

    public GameTask(AbstractPlugin<T, U, V> plugin) {
        super(plugin);
        this.gameBoard = plugin.getGameBoard();
    }

    @Override
    public long getPeriod() {
        return 20L;
    }

    @Override
    public void run() {
        super.plugin.getServer().getOnlinePlayers().forEach(player -> player.sendPlayerListHeaderAndFooter(super.gameBase.getPlayerList()[0], super.gameBase.getPlayerList()[1]));

        super.gameBase.getArenas().forEach(arena -> {
            arena.getPlayersQueue().forEach(player -> player.getPlayer().sendActionBar(Component.text("§eQueue postion: §6" + arena.getPosition(player) + "/" + arena.getMaxSlots())));

            if (arena.getState().isWait() && !arena.isQueueEmpty() && arena.canJoin()) {
                U nextPlayer = arena.getPlayersQueue().get(0);
                arena.getPlayersQueue().remove(nextPlayer);
                super.gameBase.handleGameJoin(nextPlayer, arena);
            }

            if (arena.getState() == ArenaState.STARTING) {
                arena.decrement();

                switch (arena.getTimer()) {
                    case 20, 15, 10, 5, 4, 3, 2, 1 -> arena.getPlayers().values().forEach(abstractPlayer -> {
                        abstractPlayer.sendTitle(Title.title(Component.text(""), Component.text("§e" + arena.getTimer() + "s")));
                        abstractPlayer.getPlayer().playSound(abstractPlayer.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                    });

                    case 0 -> {
                        if (arena.getPlayers().size() < super.gameBase.getMin()) {
                            arena.resetTimer();
                            arena.setState(ArenaState.WAITING);
                            arena.getPlayers().values().forEach(abstractPlayer -> abstractPlayer.getPlayer().sendMessage(Component.text("§cStart cancelled, not enough players.")));
                            return;
                        }

                        arena.resetTimer();
                        arena.setState(ArenaState.FIGHTING);
                        arena.setStartSize();

                        TextComponent startMessage = (TextComponent) super.plugin.getGameMessage().getStart(arena);
                        if (!startMessage.content().isEmpty()) {
                            super.plugin.getServer().broadcast(startMessage);
                        }

                        super.plugin.getServer().getPluginManager().callEvent(new GameStartEvent<>(arena));

                        AtomicInteger index = new AtomicInteger();
                        arena.getPlayers().values().forEach(abstractPlayer -> {
                            Player player = abstractPlayer.getPlayer();

                            U targetPlayer = super.gameBase.getPlayer(abstractPlayer.getId());
                            targetPlayer.setLastLocation(player.getLocation());
                            targetPlayer.setScoreboard(this.gameBoard.instanceGameBoard(abstractPlayer));

                            arena.teleport(player, index.getAndIncrement());
                            super.gameBase.handleStart(player);
                        });
                    }

                    default -> {
                    }
                }
            }
        });
    }
}
