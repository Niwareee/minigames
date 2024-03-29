package fr.niware.gamesapi.utils.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RInventoryTaskData {

    private final Map<UUID, Integer> runnableMap;

    public RInventoryTaskData() {
        this.runnableMap = new HashMap<>();
    }

    public Map<UUID, Integer> getRunnableMap() {
        return this.runnableMap;
    }
}