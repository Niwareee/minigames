package fr.niware.zombie.player;

import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.zombie.arena.ZombieArena;
import org.bukkit.entity.Player;

public class ZombiePlayer extends AbstractPlayer<ZombieArena> {

    public ZombiePlayer(Player player, ZombieArena arena) {
        super(player, arena);
    }

    @Override
    public void reset() {

    }

}
