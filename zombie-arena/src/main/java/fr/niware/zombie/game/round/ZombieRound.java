package fr.niware.zombie.game.round;

public class ZombieRound {

    private final RoundEnum roundEnum;
    private final int initCount;
    private int remaining;

    public ZombieRound(RoundEnum roundEnum) {
        this.roundEnum = roundEnum;
        this.initCount = roundEnum.getSizeRainbowZombie() + roundEnum.getSizeBabyZombie() + roundEnum.getSizeBoss();
        this.remaining = this.initCount;
    }

    public RoundEnum getEnum() {
        return this.roundEnum;
    }

    public int getInitCount() {
        return this.initCount;
    }

    public int getRemaining() {
        return this.remaining;
    }

    public int getAndDecrementRemaining() {
        this.remaining--;
        return this.remaining;
    }
}
