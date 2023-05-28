package fr.niware.gamesapi.game;

import fr.niware.gamesapi.file.IGameFile;
import fr.niware.gamesapi.file.ServerFile;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IGameBase<T extends AbstractPlayer<U>, U extends Arena<T>> {

    IGameFile<ServerFile> getGameFile();

    T getPlayer(UUID uuid);

    T getPlayerArena(UUID uuid);

    Collection<T> getPlayers();

    void putPlayer(T abstractPlayer);

    boolean containsPlayer(UUID uuid);

    List<U> getArenas();

    boolean isFighting(UUID uuid);

    T createPlayer(Player player, U arena);

    void clearPlayer(Player player);

    void handleJoin(Player player);

    void handleQuit(Player player);

    void handleGameJoin(T abstractPlayer, U arena);

    void handleStart(Player player);

    void handleEliminate(UUID uuid);

    void handleGameLeave(T abstractPlayer);

    void handleEnd(U arena);

    int getMin();

    int getMax();

    ItemStack[] getStartItems();

    ItemStack[] getStartArmors();

    Component[] getPlayerList();
}
