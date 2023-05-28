package fr.niware.gamesapi.game.arena;

import org.bukkit.Material;

public enum ArenaState {

    WAITING(false, "§bWaiting", Material.LIME_CONCRETE),
    STARTING(false, "§eStarting...", Material.YELLOW_CONCRETE),
    FIGHTING(true, "§cIn game", Material.RED_CONCRETE),
    FINISH(true, "§6End", Material.ORANGE_CONCRETE);

    private final boolean inProgress;
    private final String name;
    private final Material material;

    ArenaState(boolean inProgress, String name, Material material) {
        this.inProgress = inProgress;
        this.name = name;
        this.material = material;
    }

    public boolean inProgress() {
        return this.inProgress;
    }

    public boolean isGame() {
        return this == ArenaState.FIGHTING;
    }

    public boolean isWait() {
        return !this.inProgress;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }
}
