package fr.niware.gamesapi.listeners;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.config.IGameConfig;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.utils.registers.AbstractListener;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener<T extends IGameBase<U, V>, U extends AbstractPlayer<V>, V extends Arena<U>> extends AbstractListener<T, U, V> {

    private final IGameConfig gameConfig;

    public PlayerListener(AbstractPlugin<T, U, V> plugin) {
        super(plugin);
        this.gameConfig = plugin.getGameConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        super.gameBase.handleJoin(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.quitMessage(null);
        super.gameBase.handleQuit(event.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getWorld().getPVP()) {
            return;
        }
        if (super.gameBase.isFighting(event.getPlayer().getUniqueId())) {
            event.setCancelled(!this.gameConfig.getInteractable()[1]);
            return;
        }

        event.setCancelled(!this.gameConfig.getInteractable()[0]);
    }


    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(ChatRenderer.viewerUnaware((player, component, message) -> Component.text("§eHuman " + player.getName() + " §7» §f" + ((TextComponent) message).content())));
    }
}
