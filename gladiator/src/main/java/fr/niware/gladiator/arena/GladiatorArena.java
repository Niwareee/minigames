package fr.niware.gladiator.arena;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import fr.niware.gamesapi.game.arena.Arena;
import fr.niware.gladiator.player.GladiatorPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GladiatorArena extends Arena<GladiatorPlayer> {

    private final Location[] arenaLocations;

    public GladiatorArena(int id, int maxSlots, Location spawnLoc) {
        super(id, maxSlots);

        World world = spawnLoc.getWorld();
        super.locations = new Location[]{
                spawnLoc.clone(),
                new Location(world, spawnLoc.getX() - 1, spawnLoc.getY() - 5, spawnLoc.getZ() + 169, 180, 0),
        };

        this.arenaLocations = new Location[]{
                new Location(world, spawnLoc.getX() + 17, spawnLoc.getY() - 10, spawnLoc.getZ() + 191, 90, 0),
                new Location(world, spawnLoc.getX() - 20, spawnLoc.getY() - 10, spawnLoc.getZ() + 191, -90, 0),
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
    public void spawnNPC(Player player) {
        NPCLib npcLib = NPCLib.getInstance();
        for (int i = 0; i < GladiatorNPC.values().length; i++) {
            GladiatorNPC gladiatorNPC = GladiatorNPC.values()[i];
            Location location = super.locations[i].clone().add(gladiatorNPC.getCoords()[0], gladiatorNPC.getCoords()[1], gladiatorNPC.getCoords()[2]);
            location.setYaw(gladiatorNPC.getCoords()[3]);

            NPC npc = npcLib.generateNPC(player, gladiatorNPC.name(), location);
            npc.setSkin(gladiatorNPC.getTexture(), gladiatorNPC.getSignature());
            npc.setText(gladiatorNPC.getName());
            npc.setCollidable(false);
            npc.setFollowLookType(NPC.FollowLookType.NONE);
            npc.setShowOnTabList(false);
            npc.create();
            npc.show();
            npc.update();
            npc.forceUpdate();
        }
    }
}