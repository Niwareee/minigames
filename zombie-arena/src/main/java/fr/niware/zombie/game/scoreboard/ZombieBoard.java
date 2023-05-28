package fr.niware.zombie.game.scoreboard;

import fr.niware.gamesapi.scoreboard.GameBoard;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import fr.niware.zombie.game.scoreboard.list.FightBoard;
import fr.niware.zombie.game.scoreboard.list.LobbyBoard;

public class ZombieBoard extends GameBoard<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombiePlugin zombiePlugin;

    public ZombieBoard(ZombiePlugin plugin) {
        super(plugin);
        this.zombiePlugin = plugin;
    }

    @Override
    public LobbyBoard instanceLobbyBoard(ZombiePlayer abstractPlayer) {
        return new LobbyBoard(this.zombiePlugin, abstractPlayer);
    }

    @Override
    public FightBoard instanceGameBoard(ZombiePlayer abstractPlayer) {
        return new FightBoard(this.zombiePlugin, abstractPlayer);
    }
}
