package fr.niware.zombie.config;

import fr.niware.gamesapi.game.config.IGameConfig;

public class ZombieConfig implements IGameConfig {

    @Override
    public boolean[] getDamageable() {
        return new boolean[]{false, true};
    }

    @Override
    public boolean isBreakable() {
        return false;
    }

    @Override
    public boolean isPlaceable() {
        return false;
    }

    @Override
    public boolean[] getFoodable() {
        return new boolean[]{false, true};
    }

    @Override
    public boolean[] getInteractable() {
        return new boolean[]{false, true};
    }

    @Override
    public boolean isCraftable() {
        return false;
    }

    @Override
    public boolean[] getClickable() {
        return new boolean[]{false, true};
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isPickupable() {
        return false;
    }
}
