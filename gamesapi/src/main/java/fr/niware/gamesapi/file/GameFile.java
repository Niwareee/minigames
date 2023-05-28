package fr.niware.gamesapi.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.niware.gamesapi.utils.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;

public class GameFile<T> implements IGameFile<T> {

    private final Gson gson;
    private final File file;
    private final T config;
    private final String name;

    public GameFile(JavaPlugin plugin, String name) {
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
        this.file = new File(plugin.getDataFolder(), name);
        Type type = new TypeToken<ServerFile>() {
        }.getType();
        this.config = this.gson.fromJson(FileUtils.loadFile(this.file), type);
        this.name = name;

        try (InputStream inputStream = this.getClass().getResourceAsStream("/" + name)) {
            if (plugin.getDataFolder().mkdirs()) {
                plugin.getSLF4JLogger().info("{} successfully created !", plugin.getDataFolder());
            }

            if (!this.file.exists()) {
                assert inputStream != null;
                Files.copy(inputStream, this.file.toPath());
                plugin.getSLF4JLogger().info("{} successfully created !", name);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public T getConfig() {
        return this.config;
    }

    @Override
    public void save(T type) {
        String json = this.gson.toJson(type);
        FileUtils.saveFile(this.file, json);
    }
}
