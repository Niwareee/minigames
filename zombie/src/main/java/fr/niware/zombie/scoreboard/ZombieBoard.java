package fr.niware.zombie.scoreboard;

import fr.niware.gamesapi.scoreboard.GameBoard;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.player.ZombiePlayer;
import fr.niware.zombie.scoreboard.list.FightBoard;
import fr.niware.zombie.scoreboard.list.LobbyBoard;

public class ZombieBoard extends GameBoard<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombiePlugin plugin;

    public ZombieBoard(ZombiePlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public LobbyBoard instanceLobbyBoard(ZombiePlayer player) {
        return new LobbyBoard(this.plugin, player);
    }

    @Override
    public FightBoard instanceGameBoard(ZombiePlayer player) {
        return new FightBoard(this.plugin, player);
    }
}