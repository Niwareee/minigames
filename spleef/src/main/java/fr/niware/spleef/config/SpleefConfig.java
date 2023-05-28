package fr.niware.spleef.config;

import fr.niware.gamesapi.game.config.IGameConfig;

public class SpleefConfig implements IGameConfig {

    @Override
    public boolean[] getDamageable() {
        return new boolean[]{false, false};
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public boolean isPlaceable() {
        return false;
    }

    @Override
    public boolean[] getFoodable() {
        return new boolean[]{false, false};
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
        return new boolean[]{false, false};
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
