package fr.niware.zombie.game.arena;

public enum ArenaLocations {

    ARENA_1(1, -133.5, 63, -164.5),
    ARENA_2(2, 1235.5, 63, -164.5),
    ARENA_3(3, 2604.5, 63, -164.5),
    ;

    private final int id;
    private final double x;
    private final double y;
    private final double z;

    ArenaLocations(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return this.id;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}
