package fr.niware.spleef.arena;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.spleef.player.SpleefPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;

public class SpleefArena extends Arena<SpleefPlayer> {

    private final Collection<Location> brokenBlocks = new HashSet<>();

    private Location[] arenaLocations;

    public SpleefArena(int id, int maxSlots, Location spawnLoc) {
        super(id, maxSlots);

        World world = spawnLoc.getWorld();
        super.locations = new Location[]{
                spawnLoc.clone(),
                new Location(world, spawnLoc.getX(), spawnLoc.getY() - 88, spawnLoc.getZ(), spawnLoc.getYaw(), spawnLoc.getPitch())
        };
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
        this.brokenBlocks.forEach(location -> location.getBlock().setType(Material.SNOW_BLOCK));
        this.brokenBlocks.clear();
    }

    @Override
    public void spawnNPC(Player player) {
        NPCLib npcLib = NPCLib.getInstance();
        for (int i = 0; i < SpleefNPC.values().length; i++) {
            Location location = super.locations[i].clone().add(0, -1, -4);
            location.setYaw(super.locations[i].getYaw() - 180);

            SpleefNPC spleefNPC = SpleefNPC.values()[i];
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

    public void addBrokenBlock(Location location) {
        this.brokenBlocks.add(location);
    }

    public void generateArenaLocations() {
        this.arenaLocations = new Location[this.getStartSize()];
        for (int i = 0; i < this.arenaLocations.length; i++) {
            int size = 25;
            double a = i * 2.0D * Math.PI / this.arenaLocations.length;
            int x = (int) Math.round(size / 3.0D * Math.cos(a) + this.getLocations()[1].getX());
            int z = (int) Math.round(size / 3.0D * Math.sin(a) + this.getLocations()[1].getZ());

            Location location = new Location(this.getLocations()[1].getWorld(), x, 147, z);
            location.setDirection(this.getLocations()[1].clone().subtract(location).toVector());

            this.arenaLocations[i] = location;
        }
    }
}
