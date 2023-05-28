package fr.niware.gladiator.player;

import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gladiator.arena.GladiatorArena;
import org.bukkit.entity.Player;

public class GladiatorPlayer extends AbstractPlayer<GladiatorArena> {

    private double damage;

    public GladiatorPlayer(Player player, GladiatorArena arena) {
        super(player, arena);
        this.damage = 0;
    }

    public double getDamage() {
        return this.damage;
    }

    public void addDamage(double damage) {
        this.damage += damage;
    }

    @Override
    public void reset() {
        this.damage = 0;
    }
}
