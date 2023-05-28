package fr.niware.spleef;

import fr.niware.gamesapi.game.GameBase;
import fr.niware.gamesapi.utils.builder.ItemBuilder;
import fr.niware.spleef.arena.SpleefArena;
import fr.niware.spleef.player.SpleefPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class SpleefGame extends GameBase<SpleefPlayer, SpleefArena> {

    public SpleefGame(SpleefPlugin plugin) {
        super(plugin);

        World world = plugin.getGameWorld().getWorld();
        super.getArenas().add(new SpleefArena(1, this.getMax(), new Location(world, -22.5, 235, -80.5, 180, 0)));
        world.setSpawnLocation(-22, 235, -80);
    }

    @Override
    public SpleefPlayer createPlayer(Player player, SpleefArena arena) {
        return new SpleefPlayer(player, arena);
    }

    @Override
    public int getMax() {
        return 20;
    }

    @Override
    public ItemStack[] getStartItems() {
        return new ItemStack[]{
                new ItemBuilder(Material.DIAMOND_SHOVEL)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .addFlag(ItemFlag.HIDE_UNBREAKABLE)
                        .setUnbreakable()
                        .build()
        };
    }

    @Override
    public ItemStack[] getStartArmors() {
        return new ItemStack[0];
    }

    @Override
    public Component[] getPlayerList() {
        return new Component[]{
                Component.text("§7§m---------------------\n§bSpleefVerse §f1.17.1\n§6§lNFTWorlds server\n§7§m---------------------\n"),
                Component.text("\n§dOnline: §f" + this.plugin.getServer().getOnlinePlayers().size() + "\n")
        };
    }
}
