package fr.niware.zombie.listeners;

import dev.sergiferry.playernpc.api.events.NPCInteractEvent;
import fr.niware.gamesapi.gui.PlayInventory;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.zombie.ZombieGame;
import fr.niware.zombie.ZombiePlugin;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.commands.ZombieDatabase;
import fr.niware.zombie.player.ZombiePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class ZombieListener extends AbstractListener<ZombieGame, ZombiePlayer, ZombieArena> {

    private final ZombieDatabase zombieDatabase;

    public ZombieListener(ZombiePlugin plugin) {
        super(plugin);
        this.zombieDatabase = plugin.getZombieDatabase();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ZOMBIE || event.getEntity().getKiller() == null) {
            return;
        }

        ZombiePlayer zombiePlayer = super.gameBase.getPlayer(event.getEntity().getKiller().getUniqueId());
        this.zombieDatabase.addStats(zombiePlayer);

        ZombieArena arena = zombiePlayer.getArena();
        int remaining = arena.getRound().decrementRemaining();
        if (remaining == 0) {
            super.gameBase.endRound(arena);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player) || !super.gameBase.isFighting(player.getUniqueId())) {
            return;
        }

        if (event.getFinalDamage() < player.getHealth()) {
            return;
        }

        event.setCancelled(true);
        super.gameBase.handleEliminate(player.getUniqueId());
    }

    @EventHandler
    public void onNPCInteract(NPCInteractEvent event) {
        if (!event.isRightClick()) {
            return;
        }

        new PlayInventory<>(this.plugin).open(event.getPlayer());
    }
}
