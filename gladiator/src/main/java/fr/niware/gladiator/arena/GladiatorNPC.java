package fr.niware.gladiator.arena;

public enum GladiatorNPC {

    SPAWN(1,
            "§eTeleport to arena",
            "ewogICJ0aW1lc3RhbXAiIDogMTY1MDQwOTgwODUwMiwKICAicHJvZmlsZUlkIiA6ICJjZGM5MzQ0NDAzODM0ZDdkYmRmOWUyMmVjZmM5MzBiZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJSYXdMb2JzdGVycyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81ZTA1MmEyMWI4MWRiMGNiNjk4MTg1MWUyMzU5ZmY1ODBhY2JiZDc0NjU1ODQ4MjIxZDljZGJiYzQyOTk1NGRjIgogICAgfQogIH0KfQ==",
            "Jt3UgLNngXw0i3D4DADgUUAbwRuABNPISHrv7w88M+i8P5N3Hk3dfEg0GBaX4J+w62xTL3uBEGRxzcN3VE9Cp+Y1aAdadevFiFcbPeFSjLEo23uREvauSDfE52G1TVpDdv4f6z/AsDIM9bc7nDuFbJ+8WJHB5S8i8jr6DDzTfDO5GLkstDEOAT0UuaQO/w1JzKCNY12LTATfY7N+LpMXjd6NqmY3q4n8ehBCOZG+1PXaDnShDOq5qNBeq8ywfUOEEMhJkKpUPySbX8HcqUkL5X4T9ZXxe19lCTIlkQq1Cj9hCDNiI1RgAtuszqT1RTQb7rLcnjKjfwXrqUH8boSPeXnfbj6yhTNZepQO4lRfvJet9/MdgE8YyVPTTVKwT13hQGSmTr4Tx0M1Qm9/i7YbzlW5wvd1oKL5HmEsb9pKXsRSZu0GyI77KcXINy3fGGWqIfDfGwZN/GD5op9+rQhJe/kjMVMS43+imlSZX15GUm9iqgpQvnRTynrD4huiFAA4KdvDRtKg4bIS9AiLCWicrWxyxXPMTHLC79erjjt1a7RcIA2745tPK3oBgCwCy77ZisPUDYjki19Jv6co+L+L/YpKizqfUCzDzR852SstGj0Y+oVYJPwTn4sYwH40e3iZJ4K927Kmr8pvKvWLowz9tRHGODBF/jSWCKDDeiOvGPs=",
            new int[]{0, 0, 3, 180}
    ),

    PLAY(1,
            "§eClick to play",
            "ewogICJ0aW1lc3RhbXAiIDogMTY0OTAxMTk5MjYwMCwKICAicHJvZmlsZUlkIiA6ICIzZjM4YmViZGYwMWQ0MjNkYWI4MjczZjUwNGFiNGEyNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJjazM0Nzk0MjM1NzUzNzMxIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzM0MjcyODMyZjc3ZDA0ODhkM2ZmMmZjNDZlMWM2OWU3MzNiN2I3NGQ3YjE3NjM5Yjg2YmEyNzcyNmIyNDMxZDMiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",
            "F+wnVRXMCnSkXIlvR8upXvNK+M3DMxOzHv2iGt+A2RHcDYkH9Qp6PvM7Kq/mOmtlu9CQYelgfAK5RDjUDLyi4fPxMxRwzhkjUIkIs16ZOOqIn9cPadPyShGaE2pNiL7eZxN0e1ldQX2Vzq6bqH8BV0KCAfjos+dmhkSK1Zn+z5bhaSy8aCaNQyhnX6/27ERRZ4J9gz50l219wxDzlnwevdKfNiHXZ1dZnZSI/QfUUDMrV/SIMWVuxlq6qKEOuUhncuPAXabw4lqXGS56H4K4TeMZJj89pzRhoqA3BvAaywgVVaHg9dak1Xaz5GDX3zslUr9Qkboh2ZY+HAgylEdzza37beRQXLiG5KZw9gCj1z4bGJjxr8oZA1hIPPj6Gq6aptJJN1XjtaP9Oo5RNxi6PPupY7MzbLuMjAvMlnWKKM+zHVGFY3HKk0RPVHIyS4Y697xjeQ5xKYmI3/KaWwDJt2ueJ+lkoIbFlmE1pf1bWn27ZMRG1PerFZWv0/q9gl/VaQSze9ZLmEnjAqXyJ/OJ39xPO6f7U5zBiRT39khUvssOJURDgAVgtSss9Vk+1ezvUfCFeGvcMYFrpWRWQOtL/XEveE73dq/CnjTMGCTOXIBFXEA5GmkbXuPsXPGjeWcomtg2brSKfTXRGJY2ns55mYNbORL63I30Tjs4XHsvL3M=",
            new int[]{0, 0, -2, 0}
    );

    private final int id;
    private final String name;
    private final String texture;
    private final String signature;
    private final int[] coords;

    GladiatorNPC(int id, String name, String texture, String signature, int[] coords) {
        this.id = id;
        this.name = name;
        this.texture = texture;
        this.signature = signature;
        this.coords = coords;
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

    public int[] getCoords() {
        return this.coords;
    }
}
