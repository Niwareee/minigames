package fr.niware.zombie.listeners;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.events.NPCInteractEvent;
import fr.niware.gamesapi.gui.PlayInventory;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ZombieArena;
import fr.niware.zombie.game.arena.ZombieNPC;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.UUID;

public class ZombieListener extends AbstractListener<ZombieGame, ZombiePlayer, ZombieArena> {

    private final AttributeModifier speedBoost = new AttributeModifier(UUID.randomUUID(), "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_SCALAR_1);

    public ZombieListener(ZombiePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ZOMBIE) {
            return;
        }

        ZombieArena zombieArena = super.gameBase.getArenas().get(0);
        for (ZombieArena arena : super.gameBase.getArenas()) {
            if (arena.getEntities().contains(event.getEntity())) {
                zombieArena = arena;
                arena.getEntities().remove(event.getEntity());
            }
        }

        if (event.getEntity().getKiller() != null) {
            ZombiePlayer zombiePlayer = super.gameBase.getPlayerArena(event.getEntity().getKiller().getUniqueId());
            zombiePlayer.addKill();
        }

        int remaining = zombieArena.getRound().getAndDecrementRemaining();
        if (remaining == 0) {
            super.gameBase.endRound(zombieArena);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getDamager().getType() != EntityType.ZOMBIE) {
                return;
            }

            ModeledEntity entity = ModelEngineAPI.getModeledEntity(event.getDamager().getUniqueId());
            if (entity == null) {
                return;
            }

            ActiveModel model = entity.getActiveModel("zombie_boss");
            if (model == null) {
                return;
            }

            ZombieArena zombieArena = super.gameBase.getPlayer(player.getUniqueId()).getArena();
            if (model.getState("run") == null) {
                return;
            }

            zombieArena.setHitBoss(0);
            model.removeState("run", true);
            ((Attributable) event.getDamager()).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(this.speedBoost);
            return;
        }

        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        ModeledEntity entity = ModelEngineAPI.getModeledEntity(event.getEntity().getUniqueId());
        if (entity == null) {
            return;
        }

        ActiveModel model = entity.getActiveModel("zombie_boss");
        if (model == null) {
            return;
        }

        ZombieArena zombieArena = super.gameBase.getPlayer(player.getUniqueId()).getArena();
        if (zombieArena.getAndIncrementHitBoss() == 11) {
            model.addState("run", 1, 1, 1);
            ((Attributable) event.getEntity()).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(this.speedBoost);
        }

        if (zombieArena.getAndIncrementTotalHitBoss() == 59) {
            entity.getEntity().remove();

            ZombiePlayer zombiePlayer = zombieArena.getPlayer(player.getUniqueId());
            zombiePlayer.addKill();
            zombieArena.getEntities().remove(event.getEntity());

            int remaining = zombieArena.getRound().getAndDecrementRemaining();
            if (remaining == 0) {
                super.gameBase.endRound(zombieArena);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!super.gameBase.isFighting(player.getUniqueId())) {
            return;
        }

        if (event.getFinalDamage() < player.getHealth()) {
            return;
        }

        event.setCancelled(true);
        super.gameBase.handleEliminate(player.getUniqueId());
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

        NPC npc = event.getNpc();
        if ("BLEACHERS".equals(npc.getCode())) {
            new PlayInventory<>(this.plugin).open(event.getPlayer());
            return;
        }

        Player player = event.getPlayer();
        ZombieArena zombieArena = super.gameBase.getPlayer(player.getUniqueId()).getArena();
        player.teleport(zombieArena.getLocations()[ZombieNPC.valueOf(npc.getCode()).getId()]);
    }
}
