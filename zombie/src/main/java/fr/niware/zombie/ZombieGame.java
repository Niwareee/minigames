package fr.niware.zombie;

import fr.niware.gamesapi.game.GameBase;
import fr.niware.gamesapi.utils.builder.ItemBuilder;
import fr.niware.zombie.arena.ZombieArena;
import fr.niware.zombie.player.ZombiePlayer;
import fr.niware.zombie.round.RoundEnum;
import fr.niware.zombie.round.ZombieRound;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ZombieGame extends GameBase<ZombiePlayer, ZombieArena> {

    private final Random random = new Random();

    public ZombieGame(ZombiePlugin plugin) {
        super(plugin);

        World world = plugin.getGameWorld().getWorld();
        super.getArenas().add(new ZombieArena(1, this.getMax(), new Location(world, 138.5, 133, -136.5, 0, 0)));
        world.setSpawnLocation(138, 133, -136);
    }

    @Override
    public ZombiePlayer createPlayer(Player player, ZombieArena arena) {
        return new ZombiePlayer(player, arena);
    }

    @Override
    public int getMax() {
        return 4;
    }

    @Override
    public ItemStack[] getStartItems() {
        return new ItemStack[]{
                new ItemBuilder(Material.STONE_SWORD)
                        .addEnchant(Enchantment.DAMAGE_ALL, 1)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .addFlag(ItemFlag.HIDE_UNBREAKABLE)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.COOKED_BEEF, 30)
                        .build(),
                new ItemBuilder(Material.GOLDEN_APPLE, 2)
                        .build(),
        };
    }

    @Override
    public ItemStack[] getStartArmors() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                        .addEnchant(Enchantment.DURABILITY, 2)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.LEATHER_LEGGINGS)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                        .addEnchant(Enchantment.DURABILITY, 2)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                        .addEnchant(Enchantment.DURABILITY, 2)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build(),
                new ItemBuilder(Material.LEATHER_HELMET)
                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                        .addEnchant(Enchantment.DURABILITY, 2)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .setUnbreakable()
                        .build()
        };
    }

    @Override
    public Component[] getPlayerList() {
        return new Component[]{
                Component.text("§7§m---------------------\n§bZombieVerse §f1.17.1\n§6§lNFTWorlds server\n§7§m---------------------\n"),
                Component.text("\n§dOnline: §f" + this.plugin.getServer().getOnlinePlayers().size() + "\n")
        };
    }

    public void nextRound(ZombieArena arena, boolean start) {
        if (!start) {
            if (arena.getRound().getRoundEnum().getIndex() + 1 > 4) {
                arena.setRound(new ZombieRound(RoundEnum.OTHER_ROUND.increment(arena.getRound().getZombieCount())));
            } else {
                arena.setRound(new ZombieRound(RoundEnum.values()[arena.getRound().getRoundEnum().getIndex() + 1]));
            }
        }

        this.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(arena.getRound().getRoundEnum().getName()));

        int rest = arena.getRound().getZombieCount() % arena.getZombieLocations().length;
        int zombiePerLocation = arena.getRound().getZombieCount() / arena.getZombieLocations().length;

        for (Location location : arena.getZombieLocations()) {
            for (int i = 0; i < zombiePerLocation; i++) {
                this.spawnZombie(arena.getRound().getRoundEnum(), location);
            }
        }

        if (rest != 0) {
            for (int i = 0; i < rest; i++) {
                this.spawnZombie(arena.getRound().getRoundEnum(), arena.getZombieLocations()[i]);
            }
        }
    }

    public void endRound(ZombieArena arena) {
        this.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(arena.getRound().getRoundEnum().getEndMessage()));
        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> this.nextRound(arena, false), 60L);
    }

    private void spawnZombie(RoundEnum roundEnum, Location location) {
        Zombie zombie = this.plugin.getGameWorld().getWorld().spawn(location, Zombie.class);
        zombie.setAdult();
        zombie.setGlowing(true);
        zombie.setPersistent(true);
        zombie.setShouldBurnInDay(false);
        zombie.setRemoveWhenFarAway(false);

        if (roundEnum.hasArmor()) {
            if (this.random.nextInt(10) >= 3) {
                zombie.getEquipment().setArmorContents(new ItemStack[]{
                        new ItemStack(Material.IRON_BOOTS),
                        new ItemStack(Material.GOLDEN_LEGGINGS),
                        new ItemStack(Material.IRON_CHESTPLATE),
                        new ItemStack(Material.GOLDEN_HELMET),
                });
            }

            if (this.random.nextInt(10) >= 3) {
                zombie.setBaby();
            }
        }
    }
}
