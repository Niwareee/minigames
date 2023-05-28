package fr.niware.gladiator.scoreboard;

import fr.niware.gamesapi.scoreboard.GameBoard;
import fr.niware.gladiator.GladiatorGame;
import fr.niware.gladiator.GladiatorPlugin;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.player.GladiatorPlayer;
import fr.niware.gladiator.scoreboard.list.FightBoard;
import fr.niware.gladiator.scoreboard.list.LobbyBoard;

public class GladiatorBoard extends GameBoard<GladiatorGame, GladiatorPlayer, GladiatorArena> {

    private final GladiatorPlugin plugin;

    public GladiatorBoard(GladiatorPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public LobbyBoard instanceLobbyBoard(GladiatorPlayer player) {
        return new LobbyBoard(this.plugin, player);
    }

    @Override
    public FightBoard instanceGameBoard(GladiatorPlayer player) {
        return new FightBoard(this.plugin, player);
    }
}
