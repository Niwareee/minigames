package fr.niware.zombie.game.arena;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gamesapi.game.arena.ArenaState;
import fr.niware.gamesapi.utils.builder.HologramBuilder;
import fr.niware.gamesapi.utils.builder.ItemBuilder;
import fr.niware.gamesapi.utils.builder.PotionBuilder;
import fr.niware.zombie.game.ZombiePlayer;
import fr.niware.zombie.game.round.RoundEnum;
import fr.niware.zombie.game.round.ZombieRound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ZombieArena extends Arena<ZombiePlayer> {

    private final Location[] arenaLocations;
    private final Location[] zombieLocations;
    private final Collection<Entity> entities = new ArrayList<>();
    private final List<String> cachePlayers = new LinkedList<>();
    private final Random random = new Random();

    private Collection<ZombiePlayer> sortedPlayerKills = new HashSet<>();
    private Collection<ZombiePlayer> sortedPlayerTime = new HashSet<>();
    private ZombieRound round;
    private int hitBoss;
    private int totalHitBoss;

    public ZombieArena(int id, int maxSlots, Location spawnLoc) {
        super(id, maxSlots);

        World world = spawnLoc.getWorld();
        Location[] locations = {
                spawnLoc.clone(),
                new Location(world, spawnLoc.getX() + 65, spawnLoc.getY() + 5, spawnLoc.getZ() - 16, -90, 0),
                new Location(world, spawnLoc.getX() + 124, spawnLoc.getY() - 20, spawnLoc.getZ() - 7, 90, 0),

                new Location(world, spawnLoc.getX() + 97, spawnLoc.getY() - 32, spawnLoc.getZ() - 16, 0, 0),
                new Location(world, spawnLoc.getX() + 112, spawnLoc.getY() - 32, spawnLoc.getZ() - 7, 90, 0),
                new Location(world, spawnLoc.getX() + 97, spawnLoc.getY() - 32, spawnLoc.getZ() - 2, -180, 0),
                new Location(world, spawnLoc.getX() + 82, spawnLoc.getY() - 32, spawnLoc.getZ() - 7, -90, 0),

                new Location(world, spawnLoc.getX() + 97, spawnLoc.getY() - 32, spawnLoc.getZ() - 4, 0, 0),
                new Location(world, spawnLoc.getX() + 94, spawnLoc.getY() - 32, spawnLoc.getZ() - 7, 90, 0),
                new Location(world, spawnLoc.getX() + 100, spawnLoc.getY() - 32, spawnLoc.getZ() - 7, -90, 0),
                new Location(world, spawnLoc.getX() + 97, spawnLoc.getY() - 32, spawnLoc.getZ() - 10, -180, 0),

                new Location(world, spawnLoc.getX() + 97, spawnLoc.getY() - 32, spawnLoc.getZ() - 7, -180, 0),
        };
        super.locations = locations;

        Map<Location, String> holograms = new HashMap<>();
        holograms.put(new Location(world, spawnLoc.getX() + 134, spawnLoc.getY() - 10, spawnLoc.getZ() - 16), "§cYou are almost there...");
        holograms.put(new Location(world, spawnLoc.getX() + 128, spawnLoc.getY() - 17, spawnLoc.getZ() - 7), "§0Welcome to the arena of the undead");
        new HologramBuilder(holograms).build();

        this.arenaLocations = new Location[]{
                locations[3],
                locations[4],
                locations[5],
                locations[6]
        };

        this.zombieLocations = new Location[]{
                locations[7],
                locations[8],
                locations[9],
                locations[10]
        };

        this.round = new ZombieRound(RoundEnum.FIRST_ROUND);
    }

    @Override
    public Location[] getArenaLocations() {
        return this.arenaLocations;
    }

    public Location[] getZombiesLocations() {
        return this.zombieLocations;
    }

    public Location getChestLocation() {
        return this.locations[11];
    }

    public Collection<ZombiePlayer> getSortedPlayerKills() {
        return this.sortedPlayerKills;
    }

    public void updateSortedKills() {
        this.sortedPlayerKills = this.getPlayers().values().stream().sorted(Comparator.comparingInt(ZombiePlayer::getKills).reversed()).toList();
    }

    public Collection<ZombiePlayer> getSortedPlayerTime() {
        return this.sortedPlayerTime;
    }

    public void updateSortedTime() {
        this.sortedPlayerTime = this.getPlayers().values().stream().sorted(Comparator.comparingInt(ZombiePlayer::getSurvivalTime).reversed()).toList();
    }

    public Collection<Entity> getEntities() {
        return this.entities;
    }

    public List<String> getCachePlayers() {
        return this.cachePlayers;
    }

    public ZombieRound getRound() {
        return this.round;
    }

    public void setRound(ZombieRound round) {
        this.round = round;
    }

    @Override
    public void reset() {
        super.reset();
        this.round = new ZombieRound(RoundEnum.FIRST_ROUND);
        this.getChestLocation().getBlock().setType(Material.AIR);
        this.entities.forEach(Entity::remove);
        this.entities.clear();
    }

    public boolean isEnd() {
        return super.getPlayers().isEmpty() || this.round.getEnum() == RoundEnum.FIFTH_ROUND && this.round.getRemaining() == 0;
    }

    public int getHitBoss() {
        return this.hitBoss;
    }

    public void setHitBoss(int hitBoss) {
        this.hitBoss = hitBoss;
    }

    public int getAndIncrementHitBoss() {
        return this.hitBoss++;
    }

    public int getTotalHitBoss() {
        return this.totalHitBoss;
    }

    public int getAndIncrementTotalHitBoss() {
        return this.totalHitBoss++;
    }

    public void spawnNPC(Player player) {
        NPCLib npcLib = NPCLib.getInstance();
        for (int i = 0; i < ZombieNPC.values().length; i++) {
            ZombieNPC zombieNPC = ZombieNPC.values()[i];
            Location location = super.locations[i].clone().add(3, 0, 0);
            location.setYaw(90);
            if (i == 2) {
                location = super.locations[2].clone().add(-3, 0, 0);
                location.setYaw(-90);
            }

            NPC firstNpc = npcLib.getNPC(player, zombieNPC.name());
            if (firstNpc != null) {
                firstNpc.teleport(location);
                continue;
            }

            NPC npc = npcLib.generateNPC(player, zombieNPC.name(), location);
            npc.setSkin(zombieNPC.getTexture(), zombieNPC.getSignature());
            npc.setText(zombieNPC.getName());
            npc.setCollidable(false);
            npc.setFollowLookType(NPC.FollowLookType.NONE);
            npc.setShowOnTabList(false);
            npc.create();
            npc.show();
            npc.update();
            npc.forceUpdate();
        }
    }

    public void spawnChest() {
        this.getChestLocation().getBlock().setType(Material.CHEST);

        List<Integer> slots = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26));
        Chest chest = (Chest) this.getChestLocation().getBlock().getState();

        for (int i = 0; i < 3; i++) {
            Integer slot = slots.get(this.random.nextInt(slots.size()));
            chest.getBlockInventory().setItem(slot, new ItemStack(Material.GOLDEN_APPLE, 5));
            slots.remove(slot);
        }

        for (int i = 0; i < 4; i++) {
            Integer slot = slots.get(this.random.nextInt(slots.size()));
            chest.getBlockInventory().setItem(slot, new ItemStack(Material.ARROW, 10));
            slots.remove(slot);
        }

        for (int i = 0; i < 4; i++) {
            Integer slot = slots.get(this.random.nextInt(slots.size()));
            chest.getBlockInventory().setItem(slot, new PotionBuilder(Material.SPLASH_POTION, 2, PotionType.INSTANT_HEAL, false, true).build());
            slots.remove(slot);
        }

        Integer slot = slots.get(this.random.nextInt(slots.size()));
        chest.getBlockInventory().setItem(slot, new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).build());
    }

    public void teleport(Player player) {
        player.teleport(super.locations[0]);
        this.spawnNPC(player);
    }

    public void forceReset() {
        this.setState(ArenaState.WAITING);
        this.getPlayers().clear();
        this.reset();
    }
}
