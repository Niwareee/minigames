package fr.niware.zombie;

import fr.niware.gamesapi.game.GameBase;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import fr.niware.gamesapi.utils.builder.ItemBuilder;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.arena.ArenaLocations;
import fr.niware.zombie.game.arena.ZombieArena;
import fr.niware.zombie.game.round.RoundEnum;
import fr.niware.zombie.game.round.ZombieRound;
import io.lumine.xikage.mythicmobs.MythicMobs;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ZombieGame extends GameBase<ZombiePlayer, ZombieArena> {

    private final MythicMobs mythicMobs;

    public ZombieGame(ZombiePlugin plugin) {
        super(plugin);
        this.mythicMobs = MythicMobs.inst();

        World world = plugin.getGameWorld().getWorld();
        for (ArenaLocations arena : ArenaLocations.values()) {
            super.getArenas().add(new ZombieArena(arena.getId(), this.getMax(), new Location(world, arena.getX(), arena.getY(), arena.getZ(), -90, 0)));
        }
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
                new ItemBuilder(Material.BOW, 1)
                        .build(),
                new ItemBuilder(Material.COOKED_BEEF, 30)
                        .build(),
                new ItemBuilder(Material.GOLDEN_APPLE, 10)
                        .build(),
                null,
                null,
                null,
                null,
                new ItemBuilder(Material.ARROW, 32)
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
                Component.text("§7§m---------------------\n§bCryptoZombiez §f1.17.1\n§6§lNFTWorlds server\n§7§m---------------------\n"),
                Component.text("\n§dOnline: §f" + this.plugin.getServer().getOnlinePlayers().size() + "\n")
        };
    }

    public void nextRound(ZombieArena arena, boolean isStart) {
        if (!isStart) {
            arena.setRound(new ZombieRound(RoundEnum.values()[arena.getRound().getEnum().getIndex() + 1]));
        }

        RoundEnum roundEnum = arena.getRound().getEnum();
        arena.getPlayers().values().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(roundEnum.getStartMessage()));

        this.spreadZombies(arena, roundEnum.getSizeBabyZombie(), location -> {
            Zombie zombie = location.getWorld().spawn(location, Zombie.class);
            zombie.setBaby();
            arena.getEntities().add(zombie);
        });
        this.spreadZombies(arena, roundEnum.getSizeRainbowZombie(), location -> arena.getEntities().add(this.mythicMobs.getMobManager().spawnMob("rainbowzombie", location).getEntity().getBukkitEntity()));
        this.spreadZombies(arena, roundEnum.getSizeBoss(), location -> arena.getEntities().add(this.mythicMobs.getMobManager().spawnMob("zombie_boss", location).getEntity().getBukkitEntity()));
    }

    public void endRound(ZombieArena arena) {
        ZombiePlayer topKiller = arena.getSortedPlayerKills().toArray(new ZombiePlayer[0])[0];
        arena.getPlayers().values().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(String.format("§6Survivors: §f%s %n§6Top killer: §c%s", arena.getPlayers().values().stream().map(AbstractPlayer::getName).collect(Collectors.joining(", ")), topKiller.getName())));

        if (arena.isEnd()) {
            this.handleEnd(arena);
            return;
        }

        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> this.nextRound(arena, false), 60L);
    }

    private void spreadZombies(ZombieArena arena, int size, Consumer<Location> spawnFunction) {
        if (size == 0) {
            return;
        }

        int rest = size % arena.getZombiesLocations().length;
        int zombiePerLocation = size / arena.getZombiesLocations().length;

        for (Location location : arena.getZombiesLocations()) {
            for (int i = 0; i < zombiePerLocation; i++) {
                spawnFunction.accept(location);
            }
        }

        if (rest != 0) {
            for (int i = 0; i < rest; i++) {
                spawnFunction.accept(arena.getZombiesLocations()[i]);
            }
        }
    }
}
