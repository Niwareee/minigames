package fr.niware.spleef.arena;

public enum SpleefNPC {

    SPAWN(1,
            "§eClick to play",
            "ewogICJ0aW1lc3RhbXAiIDogMTYyMDQ4MjY3NDg3MywKICAicHJvZmlsZUlkIiA6ICJkYmNlZjMyZjI5ZDc0Y2UzOTUzOWMwYjBhMTE1YjZiZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJyYW1waXJlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I2NjRkNDljOTdlZmMzZDVhMmJjMmMyZjhjMDQ5NzFmYjI5NjU3NGE0Yjc3NWVkN2YxNjQ2NzhkNTRmYTE4ZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
            "EXkmYr2YqYNGSjFWrW1RaKyH9a3eyM/gj4fHtnT7Ddv0YuvEMjcz6r6Jkc/PCyMu/VbMZS1GUmnhSZ8PvTdnu7IXpttOl8s7vvgNnifhCktkfMVrCm5939E4CPU5bVmr2W2ThigFifHGCKZc/aZ0CxlM3YrJFpaxVTG++ScWCp1vn5HIi9IleWl5RsXk2ZxHF7Cl2VAar7x5anqEvk2aGs2InfgnHUcwP3RRDs3nWIO7lmnITjYuqRwIxs3rsMmLwgG5/jSNujq+rU/Zfe0CUbUh8tlasQjIh46Aohk/XT6FqoabXLW2FHg67m0sXLEQb1mkbir8sz9wZzFdy4+OTaewL3zu+yuBekm91uIWTGnsFEtrv9Csznt93vVZHzyEmnNtBD/kGd48VOPMUB+xjkhDdKSAJmQ4CEtWpU5WIEuZ7P6FwwzrzQcoQWFANhZGMxRSE7vBdwdkQGBoPInkd55gCe4sSfzQA0qYsaA59gmBr85zCcj3a04/9cG/HGrGd6oPuinNhfdDtwPHy2SmuUCuhpyJAGsxfyIUgiCbB2bjyRAVEbt3CFZt/COsxaSRbiYwrBIgqTGw9MDR+W/iEUw/llH66wU9hVXD6u6NXEua0sBIAflbSrKBwfDqb4yOKQVeNAxDSiLoUtpYgFjfPpgC7dLM29ANLvETAKznwmw="
    );

    private final int id;
    private final String name;
    private final String texture;
    private final String signature;

    SpleefNPC(int id, String name, String texture, String signature) {
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
