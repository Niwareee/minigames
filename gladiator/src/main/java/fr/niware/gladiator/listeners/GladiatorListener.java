package fr.niware.gladiator.listeners;

import dev.sergiferry.playernpc.api.events.NPCInteractEvent;
import fr.niware.gamesapi.gui.PlayInventory;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.gladiator.GladiatorGame;
import fr.niware.gladiator.GladiatorPlugin;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.player.GladiatorPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class GladiatorListener extends AbstractListener<GladiatorGame, GladiatorPlayer, GladiatorArena> {

    public GladiatorListener(GladiatorPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        event.getEntity().remove();
    }

    @EventHandler
    public void onNPCInteract(NPCInteractEvent event) {
        if (!event.isRightClick()) {
            return;
        }

        if ("SPAWN".equals(event.getNpc().getCode())) {
            GladiatorArena arena = super.gameBase.getPlayer(event.getPlayer().getUniqueId()).getArena();
            event.getPlayer().teleport(arena.getLocations()[1]);
            return;
        }

        new PlayInventory<>(this.plugin).open(event.getPlayer());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!super.gameBase.isFighting(event.getEntity().getUniqueId()) || !(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event.getFinalDamage() < player.getHealth()) {
            return;
        }

        event.setCancelled(true);
        super.gameBase.handleEliminate(player.getUniqueId());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        super.gameBase.getPlayerArena(event.getEntity().getUniqueId()).addDamage(event.getFinalDamage() / 2);
    }
}

