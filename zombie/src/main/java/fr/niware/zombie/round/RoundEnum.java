package fr.niware.zombie.round;

import net.kyori.adventure.text.Component;

public enum RoundEnum {

    FIRST_ROUND(0,"§bFirst round", "§aCongrats for this round ! Ready for the 2nd ?", false, 15),
    SECOND_ROUND(1, "§bSecond round", "§aIncredible ! The next round will be harder !", false, 35),
    THIRD_ROUND(2, "§b3rd round", "§aAre you ready for the next one ?", true, 65),
    FOURTH_ROUND(3, "§b4th round", "§aYou are amazing! Ready for the next one ?", true, 100),
    OTHER_ROUND(4, "§bRound %s", "§aYou are amazing! Ready for the next one ?", true, 40),
    ;

    private final int index;
    private final String name;
    private final String endMessage;
    private final boolean hasArmor;

    private int zombieCount;

    RoundEnum(int index, String name, String endMessage, boolean hasArmor, int zombieCount) {
        this.index = index;
        this.name = name;
        this.endMessage = endMessage;
        this.hasArmor = hasArmor;
        this.zombieCount = zombieCount;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public Component getEndMessage() {
        return Component.text(this.endMessage);
    }

    public boolean hasArmor() {
        return this.hasArmor;
    }

    public int getZombieCount() {
        return this.zombieCount;
    }

    public RoundEnum increment(int value) {
        this.zombieCount = this.zombieCount + value;
        return this;
    }
}
