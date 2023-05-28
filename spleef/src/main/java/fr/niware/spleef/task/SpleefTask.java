package fr.niware.spleef.task;

import fr.niware.gamesapi.utils.registers.AbstractTask;
import fr.niware.spleef.SpleefGame;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.player.SpleefPlayer;

import java.util.ArrayList;
import java.util.Collection;

public class SpleefTask extends AbstractTask<SpleefGame, SpleefPlayer, SpleefArena> {

    public SpleefTask(SpleefPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        super.gameBase.getArenas().forEach(arena -> {
            Collection<SpleefPlayer> spleefPlayers = new ArrayList<>(arena.getPlayers().values());
            spleefPlayers.forEach(player -> {
                if (player.getPlayer().getLocation().getBlockY() < 139) {
                    super.gameBase.handleEliminate(player.getId());
                }
            });
        });
    }

    @Override
    public long getPeriod() {
        return 2L;
    }
}
