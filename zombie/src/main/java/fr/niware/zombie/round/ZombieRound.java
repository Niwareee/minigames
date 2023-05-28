package fr.niware.zombie.round;

public class ZombieRound {

    private final RoundEnum roundEnum;
    private final long startMillis;

    private int remaining;

    public ZombieRound(RoundEnum roundEnum) {
        this.roundEnum = roundEnum;
        this.startMillis = System.currentTimeMillis();

        this.remaining = roundEnum.getZombieCount();
    }

    public RoundEnum getRoundEnum() {
        return this.roundEnum;
    }

    public int getRemaining() {
        return this.remaining;
    }

    public long getStartMillis() {
        return this.startMillis;
    }

    public int decrementRemaining() {
        this.remaining--;
        return this.remaining;
    }

    public int getZombieCount() {
        return this.roundEnum.getZombieCount();
    }
}
