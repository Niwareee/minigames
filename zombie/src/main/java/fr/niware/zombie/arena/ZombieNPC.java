package fr.niware.zombie.arena;

public enum ZombieNPC {

    SPAWN(1,
            "§eClick to play",
            "ewogICJ0aW1lc3RhbXAiIDogMTY1MTI2NzM4MTc0NCwKICAicHJvZmlsZUlkIiA6ICJkYmQyYjU4N2VjMWY0ZTgxYTNkOGFlODM5OWJiMDFjMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJjYXRzaW5zcGFjZWUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjA4Y2JjOWVjOTY0MGZmMTc4MDdkMGE0NzVjODE5MDI2OTc5OWE3MDA5OTgyNTM2MjcwM2I1N2NkYzFiZTlmMCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
            "ZSGAoPARRFkazR978PUQq51WuJJUVWFGCYFvN5WvQSXx5VRXvtn/F/K1KXQqYigBNSS7ycp9m2A6r5sMMclXPLr+4rNf5D4+WTZH9gV0GEQbtUtzfjqhCGZOZCdX+7+lEEeLPJj38rjHhiWn3gUSiLujbnp0RQEv3jndKx0ecxHoS3HBM6UJhTKEcptqkEcDj36Ii/o/WaERHw5U0Uh9VhxF+vnNdF+VVw8AgJiGK5VS/ZRnN1g8qXLC84too+Vt0YlgSsh+qto21ermcY0pMLS37HBUVfqM8xPG/LDn2G6+VK1OMtwi7uwre+Bw//bEyE1lIEk7J70SyZ6MB/OQ4Z823AE2ILZg+UuZhn9lzE2HVOfgWSWUNsqys4O127vyH9gKH1D6eKfbJcuqpm4rE8ixDBkE5RsMF6GQG0JeYnwmIlY+Of5ta6MtL0IHC3HVtUKFpx0tGipIxl9gm43jrhnnjKy1p4lP+5//n+0TYyiDaFT07E3T5KcvCoa4bj9eXZjd9KRz5RGOMI9i/gAhdpnXv7y4xof5L3gO+I/jAW7LeFTq40OexhM9DgTaMeQkxF4t7nICkCWMvkZxZTyozeS3GiQGTE5rq40zRc57TsuFGkon+iXf7jRCMigexZ2mZ+88Vn1UJ/uIfLdzFA/ovCuvxUOiBg+YeklXNwOPH/M="
    );

    private final int id;
    private final String name;
    private final String texture;
    private final String signature;

    ZombieNPC(int id, String name, String texture, String signature) {
        this.id = id;
        this.name = name;
        this.texture = texture;
        this.signature = signature;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getTexture() {
        return this.texture;
    }

    public String getSignature() {
        return this.signature;
    }
}
