package fr.niware.gamesapi.gui;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.IGameBase;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.utils.builder.ItemBuilder;
import fr.niware.gamesapi.utils.gui.RInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayInventory<T extends AbstractPlayer<?>> extends RInventory<T> {

    private final IGameBase<T, ?> gameBase;

    public PlayInventory(AbstractPlugin<?, T, ?> plugin) {
        super(plugin);
        this.gameBase = plugin.getGameBase();
    }

    @Override
    public void open(Player player) {
        this.gameBase.getArenas().forEach(arena -> this.setItem(arena.getId() + 10, new ItemBuilder(arena.getState().getMaterial())
                .setName("§aArena §f#" + arena.getId())
                .addLoreLine("§7Players: §6" + arena.getPlayers().size() + "/" + this.gameBase.getMax())
                .addLoreLine("§7State: " + arena.getState().getName())
                .build(), clickEvent -> {

            if (this.gameBase.containsPlayer(player.getUniqueId())) {
                player.sendActionBar(Component.text("§cError: You have already join a game."));
                player.closeInventory();
                return;
            }

            T abstractPlayer = this.gameBase.getPlayer(player.getUniqueId());
            arena.getPlayersQueue().add(abstractPlayer);

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            player.sendMessage("§aYou have been added to the queue.");
            player.closeInventory();
        }));

        super.open(player);
    }

    @Override
    protected String getName() {
        return "Select arena";
    }

    @Override
    protected int getSize() {
        return 6;
    }
}
