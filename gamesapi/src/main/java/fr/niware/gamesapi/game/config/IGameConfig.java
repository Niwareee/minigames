package fr.niware.gamesapi.game.config;

public interface IGameConfig {

    boolean[] getDamageable();

    boolean isBreakable();

    boolean isPlaceable();

    boolean[] getFoodable();

    boolean[] getInteractable();

    boolean isCraftable();

    boolean[] getClickable();

    boolean isDropable();

    boolean isPickupable();
}
