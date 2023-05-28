package fr.niware.gladiator.commands;

import fr.niware.gamesapi.database.SQLDatabase;
import fr.niware.gladiator.GladiatorPlugin;
import fr.niware.gladiator.player.GladiatorPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GladiatorDatabase {

    private static final String TABLE_NAME = "gladiator";

    private final GladiatorPlugin plugin;
    private final SQLDatabase sqlDatabase;
    private final Map<UUID, GladiatorProfil> topPlayers = new HashMap<>();
    private final Map<UUID, GladiatorProfil> cachePlayers = new HashMap<>();

    public GladiatorDatabase(GladiatorPlugin plugin) {
        this.plugin = plugin;
        this.sqlDatabase = plugin.getSQLDatabase();
        this.handleStart();
    }

    public Map<UUID, GladiatorProfil> getTopPlayers() {
        return this.topPlayers;
    }

    private void createIfNotExists(UUID uuid, String name) {
        String request = "SELECT kills FROM " + GladiatorDatabase.TABLE_NAME + " WHERE player_uuid=?";
        this.sqlDatabase.executeQuery(request, statement -> statement.setString(1, uuid.toString()), resultSet -> {
            if (!resultSet.next()) {
                String insertRequest = "INSERT INTO " + GladiatorDatabase.TABLE_NAME + " (player_uuid, player_name) VALUES (?, ?)";
                this.sqlDatabase.executeQuery(insertRequest, statement -> {
                    statement.setString(1, uuid.toString());
                    statement.setString(2, name);
                });
            }
            return null;
        });
    }

    public void handleStop() {
        String request = "UPDATE " + GladiatorDatabase.TABLE_NAME + " SET kills=? WHERE player_uuid=?";
        this.cachePlayers.values().forEach(profile -> {
            this.createIfNotExists(profile.getId(), profile.getName());
            this.sqlDatabase.executeQuery(request, statement -> {
                statement.setInt(1, profile.getKills());
                statement.setString(2, profile.getId().toString());
            });
        });
    }

    public void handleStart() {
        this.sqlDatabase.update("CREATE TABLE IF NOT EXISTS " + GladiatorDatabase.TABLE_NAME + " (id int(11) AUTO_INCREMENT NOT NULL UNIQUE, player_uuid VARCHAR(255) NOT NULL UNIQUE, player_name TEXT NOT NULL, kills INT NOT NULL DEFAULT '0')");

        String request = "SELECT player_uuid, player_name, kills FROM " + GladiatorDatabase.TABLE_NAME;
        this.sqlDatabase.executeQuery(request, statement -> {}, resultSet -> {
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("player_uuid"));
                this.topPlayers.put(uuid, new GladiatorProfil(uuid, resultSet.getString("player_name"), resultSet.getInt("kills")));
            }
            return null;
        });

        this.cachePlayers.putAll(this.topPlayers);
        this.plugin.getLog4JLogger().info("Load {} profiles from database.", this.topPlayers.size());
    }

    public void addStats(GladiatorPlayer gladiatorPlayer) {
        this.cachePlayers.putIfAbsent(gladiatorPlayer.getId(), new GladiatorProfil(gladiatorPlayer.getId(), gladiatorPlayer.getName()));
        this.cachePlayers.get(gladiatorPlayer.getId()).addKills();
    }

    public void resetStats() {
        this.cachePlayers.values().forEach(cache -> cache.setKills(0));

        String request = "UPDATE " + GladiatorDatabase.TABLE_NAME + " SET kills=? WHERE player_uuid=?";
        this.cachePlayers.values().forEach(profile -> {
            profile.setKills(0);
            this.createIfNotExists(profile.getId(), profile.getName());
            this.sqlDatabase.executeQuery(request, statement -> {
                statement.setInt(1, 0);
                statement.setString(2, profile.getId().toString());
            });
        });
    }

}
