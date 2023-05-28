package fr.niware.zombie.arena;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.zombie.player.ZombiePlayer;
import fr.niware.zombie.round.RoundEnum;
import fr.niware.zombie.round.ZombieRound;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ZombieArena extends Arena<ZombiePlayer> {

    private final Location[] arenaLocations;
    private final Location[] zombieLocations;

    private ZombieRound round;

    public ZombieArena(int id, int maxSlots, Location spawnLoc) {
        super(id, maxSlots);

        World world = spawnLoc.getWorld();
        super.locations = new Location[]{
                spawnLoc.clone(),
        };

        this.arenaLocations = new Location[]{
                new Location(world, spawnLoc.getX() - 5.5, spawnLoc.getY() - 64, spawnLoc.getZ() + 65, -180, 0),
                new Location(world, spawnLoc.getX() - 71.5, spawnLoc.getY() - 64, spawnLoc.getZ() - 1, -90, 0),
                new Location(world, spawnLoc.getX() - 5.5, spawnLoc.getY() - 64, spawnLoc.getZ() - 67.5, 0, 0),
                new Location(world, spawnLoc.getX() - 60, spawnLoc.getY() - 64, spawnLoc.getZ() - 1, 90, 0),
        };

        this.zombieLocations = new Location[]{
                new Location(world, spawnLoc.getX() - 5.5, spawnLoc.getY() - 65, spawnLoc.getZ() - 53, 180, 0),
                new Location(world, spawnLoc.getX() + 31.5, spawnLoc.getY() - 65, spawnLoc.getZ() - 38, 45, 0),
                new Location(world, spawnLoc.getX() + 46, spawnLoc.getY() - 65, spawnLoc.getZ() - 1, 90, 0),
                new Location(world, spawnLoc.getX() - 31.5, spawnLoc.getY() - 65, spawnLoc.getZ() + 36, 135, 0),
                new Location(world, spawnLoc.getX() - 5.5, spawnLoc.getY() - 64, spawnLoc.getZ() + 51, 180, 0),
                new Location(world, spawnLoc.getX() - 42, spawnLoc.getY() - 65, spawnLoc.getZ() + 36, -135, 0),
                new Location(world, spawnLoc.getX() - 57, spawnLoc.getY() - 65, spawnLoc.getZ() - 1, -90, 0),
                new Location(world, spawnLoc.getX() - 42, spawnLoc.getY() - 66, spawnLoc.getZ() - 38, -45, 0),
        };

        this.round = new ZombieRound(RoundEnum.FIRST_ROUND);
    }

    @Override
    protected Location[] getArenaLocations() {
        return this.arenaLocations;
    }

    @Override
    public boolean isEnd() {
        return this.getPlayers().size() <= 1;
    }

    @Override
    public void reset() {
        super.reset();
        this.round = new ZombieRound(RoundEnum.FIRST_ROUND);
        this.locations[0].getWorld().getEntities()
                .stream()
                .filter(entity -> entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND)
                .forEach(Entity::remove);
    }

    @Override
    public void spawnNPC(Player player) {
        NPCLib npcLib = NPCLib.getInstance();
        for (int i = 0; i < ZombieNPC.values().length; i++) {
            Location location = super.locations[i].clone().add(0, -1, 4);
            location.setYaw(180);

            ZombieNPC spleefNPC = ZombieNPC.values()[i];
            NPC npc = npcLib.generateNPC(player, spleefNPC.name(), location);
            npc.setSkin(spleefNPC.getTexture(), spleefNPC.getSignature());
            npc.setText(spleefNPC.getName());
            npc.setCollidable(false);
            npc.setFollowLookType(NPC.FollowLookType.NONE);
            npc.setShowOnTabList(false);
            npc.create();
            npc.show();
            npc.update();
            npc.forceUpdate();
        }
    }

    public Location[] getZombieLocations() {
        return this.zombieLocations;
    }

    public ZombieRound getRound() {
        return this.round;
    }

    public void setRound(ZombieRound round) {
        this.round = round;
    }
}

