package fr.niware.zombie.game.round;

import net.kyori.adventure.text.Component;

public enum RoundEnum {

    FIRST_ROUND(0, "§bFirst round", 3, 0, 0),
    SECOND_ROUND(1, "§bSecond round", 5, 3, 0),
    THIRD_ROUND(2, "§b3rd round", 7, 5, 0),
    FOURTH_ROUND(3, "§b4th round", 10, 5, 0),
    FIFTH_ROUND(4, "§bLast round", 12, 0, 1),
    ;

    private final int index;
    private final String startMessage;
    private final int sizeRainbowZombie;
    private final int sizeBabyZombie;
    private final int sizeBoss;

    RoundEnum(int index, String startMessage, int sizeRainbowZombie, int sizeBabyZombie, int sizeBoss) {
        this.index = index;
        this.startMessage = startMessage;
        this.sizeRainbowZombie = sizeRainbowZombie;
        this.sizeBabyZombie = sizeBabyZombie;
        this.sizeBoss = sizeBoss;
    }

    public int getIndex() {
        return this.index;
    }

    public Component getStartMessage() {
        return Component.text(this.startMessage);
    }

    public int getSizeRainbowZombie() {
        return this.sizeRainbowZombie;
    }

    public int getSizeBabyZombie() {
        return this.sizeBabyZombie;
    }

    public int getSizeBoss() {
        return this.sizeBoss;
    }
}
