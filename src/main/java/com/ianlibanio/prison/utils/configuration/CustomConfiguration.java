package com.ianlibanio.prison.utils.configuration;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfiguration {

    @Getter File file;
    @Getter FileConfiguration configuration;

    String name;
    JavaPlugin plugin;

    public <T extends JavaPlugin> CustomConfiguration(T plugin, String name) {
        this.name = name;
        this.plugin = plugin;

        final String customFile = name + ".yml";

        file = new File(plugin.getDataFolder(), customFile);
        if (!file.exists()) {
            plugin.saveResource(customFile, false);
        }

        this.configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Object get(String path) {
        return configuration.get(path);
    }

    public Object get(String path, String def) {
        return configuration.get(path, def);
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        configuration.set(path, value);
        save();
    }

}