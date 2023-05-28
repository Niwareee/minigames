package fr.niware.gladiator;

import fr.niware.gamesapi.game.GameBase;
import fr.niware.gamesapi.utils.builder.ItemBuilder;
import fr.niware.gladiator.arena.GladiatorArena;
import fr.niware.gladiator.player.GladiatorPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class GladiatorGame extends GameBase<GladiatorPlayer, GladiatorArena> {

    public GladiatorGame(GladiatorPlugin plugin) {
        super(plugin);

        World world = plugin.getGameWorld().getWorld();
        super.getArenas().add(new GladiatorArena(1, this.getMax(), new Location(world, -14.5, 80, -321.5, 0, 0)));
        world.setSpawnLocation(-14, 80, -321);
    }

    @Override
    public GladiatorPlayer createPlayer(Player player, GladiatorArena arena) {
        return new GladiatorPlayer(player, arena);
    }

    @Override
    public int getMax() {
        return 2;
    }

    @Override
    public ItemStack[] getStartItems() {
        return new ItemStack[]{
                new ItemBuilder(Material.STONE_SWORD)
                        .addEnchant(Enchantment.DAMAGE_ALL, 1)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .addFlag(ItemFlag.HIDE_UNBREAKABLE)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.BOW, 1)
                        .addFlag(ItemFlag.HIDE_UNBREAKABLE)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.GOLDEN_APPLE, 2)
                        .build(),
                new ItemBuilder(Material.COOKED_BEEF, 16)
                        .build(),
                new ItemBuilder(Material.ARROW, 5)
                        .build()
        };
    }

    @Override
    public ItemStack[] getStartArmors() {
        return new ItemStack[]{
                new ItemBuilder(Material.IRON_BOOTS)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.IRON_LEGGINGS)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.IRON_CHESTPLATE)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.IRON_HELMET)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build()
        };
    }

    @Override
    public Component[] getPlayerList() {
        return new Component[]{
                Component.text("§7§m---------------------\n§bGladiaVerse §f1.17.1\n§6§lNFTWorlds server\n§7§m---------------------\n"),
                Component.text("\n§dOnline: §f" + this.plugin.getServer().getOnlinePlayers().size() + "\n")
        };
    }
}
