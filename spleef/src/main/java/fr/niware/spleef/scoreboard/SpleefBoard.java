package fr.niware.spleef.scoreboard;

import fr.niware.gamesapi.scoreboard.GameBoard;
import fr.niware.spleef.SpleefGame;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.player.SpleefPlayer;
import fr.niware.spleef.scoreboard.list.FightBoard;
import fr.niware.spleef.scoreboard.list.LobbyBoard;

public class SpleefBoard extends GameBoard<SpleefGame, SpleefPlayer, SpleefArena> {

    private final SpleefPlugin plugin;

    public SpleefBoard(SpleefPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public LobbyBoard instanceLobbyBoard(SpleefPlayer player) {
        return new LobbyBoard(this.plugin, player);
    }

    @Override
    public FightBoard instanceGameBoard(SpleefPlayer player) {
        return new FightBoard(this.plugin, player);
    }
}