package fr.niware.spleef.listeners;

import dev.sergiferry.playernpc.api.events.NPCInteractEvent;
import fr.niware.gamesapi.game.arena.ArenaState;
import fr.niware.gamesapi.gui.PlayInventory;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import fr.niware.spleef.SpleefGame;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.player.SpleefPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class SpleefListener extends AbstractListener<SpleefGame, SpleefPlayer, SpleefArena> {

    public SpleefListener(SpleefPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        SpleefArena arena = super.gameBase.getPlayer(event.getPlayer().getUniqueId()).getArena();
        if (arena.getState() != ArenaState.FIGHTING) {
            event.setCancelled(true);
            return;
        }

        if (event.getBlock().getType() != Material.SNOW_BLOCK) {
            event.setCancelled(true);
            return;
        }

        arena.getPlayer(event.getPlayer().getUniqueId()).addBrokenBlocks();
        arena.addBrokenBlock(event.getBlock().getLocation());
    }

    @EventHandler
    public void onNPCInteract(NPCInteractEvent event) {
        if (!event.isRightClick()) {
            return;
        }

        new PlayInventory<>(this.plugin).open(event.getPlayer());
    }
}
