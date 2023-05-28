package fr.niware.spleef.player;

import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.spleef.arena.SpleefArena;
import org.bukkit.entity.Player;

public class SpleefPlayer extends AbstractPlayer<SpleefArena> {

    private int brokenBlocks;

    public SpleefPlayer(Player player, SpleefArena arena) {
        super(player, arena);
        this.brokenBlocks = 0;
    }

    public int getBrokenBlocks() {
        return this.brokenBlocks;
    }

    public void addBrokenBlocks() {
        this.brokenBlocks++;
    }

    @Override
    public void reset() {
        this.brokenBlocks = 0;
    }
}
