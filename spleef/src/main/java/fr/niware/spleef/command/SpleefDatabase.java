package fr.niware.spleef.command;

import fr.niware.gamesapi.database.SQLDatabase;
import fr.niware.spleef.SpleefPlugin;
import fr.niware.spleef.player.SpleefPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpleefDatabase {

    private static final String TABLE_NAME = "spleef";

    private final SpleefPlugin plugin;
    private final SQLDatabase sqlDatabase;
    private final Map<UUID, SpleefProfil> topPlayers = new HashMap<>();
    private final Map<UUID, SpleefProfil> cachePlayers = new HashMap<>();

    public SpleefDatabase(SpleefPlugin plugin) {
        this.plugin = plugin;
        this.sqlDatabase = plugin.getSQLDatabase();
        this.handleStart();
    }

    public Map<UUID, SpleefProfil> getTopPlayers() {
        return this.topPlayers;
    }

    public void createIfNotExists(UUID uuid, String name) {
        String request = "SELECT wins FROM " + SpleefDatabase.TABLE_NAME + " WHERE player_uuid=?";
        this.sqlDatabase.executeQuery(request, statement -> statement.setString(1, uuid.toString()), resultSet -> {
            if (!resultSet.next()) {
                String insertRequest = "INSERT INTO " + SpleefDatabase.TABLE_NAME + " (player_uuid, player_name) VALUES (?, ?)";
                this.sqlDatabase.executeQuery(insertRequest, statement -> {
                    statement.setString(1, uuid.toString());
                    statement.setString(2, name);
                });
            }
            return null;
        });
    }

    public void handleStop() {
        String request = "UPDATE " + SpleefDatabase.TABLE_NAME + " SET wins=? WHERE player_uuid=?";
        this.cachePlayers.values().forEach(profile -> {
            this.createIfNotExists(profile.getId(), profile.getName());
            this.sqlDatabase.executeQuery(request, statement -> {
                statement.setInt(1, profile.getWins());
                statement.setString(2, profile.getId().toString());
            });
        });
    }

    public void handleStart() {
        this.sqlDatabase.update("CREATE TABLE IF NOT EXISTS " + SpleefDatabase.TABLE_NAME + " (id int(11) AUTO_INCREMENT NOT NULL UNIQUE, player_uuid VARCHAR(255) NOT NULL UNIQUE, player_name TEXT NOT NULL, wins INT NOT NULL DEFAULT '0')");

        String request = "SELECT player_uuid, player_name, wins FROM " + SpleefDatabase.TABLE_NAME;
        this.sqlDatabase.executeQuery(request, statement -> {}, resultSet -> {
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("player_uuid"));
                this.topPlayers.put(uuid, new SpleefProfil(uuid, resultSet.getString("player_name"), resultSet.getInt("wins")));
            }
            return null;
        });

        this.cachePlayers.putAll(this.topPlayers);
        this.plugin.getLog4JLogger().info("Load {} profiles from database.", this.topPlayers.size());
    }

    public void addStats(SpleefPlayer spleefPlayer) {
        this.cachePlayers.putIfAbsent(spleefPlayer.getId(), new SpleefProfil(spleefPlayer.getId(), spleefPlayer.getName()));
        this.cachePlayers.get(spleefPlayer.getId()).addWins();
    }

    public void resetStats() {
        this.cachePlayers.values().forEach(cache -> cache.setWins(0));

        String request = "UPDATE " + SpleefDatabase.TABLE_NAME + " SET wins=? WHERE player_uuid=?";
        this.cachePlayers.values().forEach(profile -> {
            profile.setWins(0);
            this.createIfNotExists(profile.getId(), profile.getName());
            this.sqlDatabase.executeQuery(request, statement -> {
                statement.setInt(1, 0);
                statement.setString(2, profile.getId().toString());
            });
        });
    }

}
