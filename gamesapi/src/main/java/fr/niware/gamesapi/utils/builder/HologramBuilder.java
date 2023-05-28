package fr.niware.gamesapi.utils.builder;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

import java.util.Map;

public class HologramBuilder {

    private Map<Location, String> hologramsMap;
    private Location location;
    private String line;

    public HologramBuilder(World world, double x, double y, double z) {
        this.location = new Location(world, x, y, z);
    }

    public HologramBuilder(Map<Location, String> hologramsMap) {
        this.hologramsMap = hologramsMap;
    }

    public void build() {
        if (this.hologramsMap.isEmpty()) {
            this.spawnHologram(this.location, this.line);
            return;
        }

        this.hologramsMap.forEach(this::spawnHologram);
    }

    private void spawnHologram(Location location, String line) {
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setMarker(true);
        armorStand.setInvisible(true);
        armorStand.setGravity(false);
        armorStand.setCustomName(line);
        armorStand.setCustomNameVisible(true);
    }
}
