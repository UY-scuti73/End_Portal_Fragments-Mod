package xyz.quazaros.epfragments73.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "loot_tables.json";
    private static final String DELAY_FILE_NAME = "tick_delay.json";
    private static ModConfig INSTANCE;
    private static DelayConfig DELAY_INSTANCE;

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
    }

    private static Path delayPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(DELAY_FILE_NAME);
    }

    public static ModConfig get() {
        if (INSTANCE == null) load();
        return INSTANCE;
    }

    public static DelayConfig getDelay() {
        if (DELAY_INSTANCE == null) load();
        return DELAY_INSTANCE;
    }

    public static void load() {
        Path p = path();
        if (Files.notExists(p)) {
            INSTANCE = new ModConfig();
            INSTANCE.validate();
            save();
            return;
        }
        try {
            String json = Files.readString(p);
            ModConfig cfg = GSON.fromJson(json, ModConfig.class);
            if (cfg == null) cfg = new ModConfig();
            cfg.validate();
            INSTANCE = cfg;
        } catch (Exception e) {
            // fallback to safe defaults
            INSTANCE = new ModConfig();
            INSTANCE.validate();
            save();
        }

        p = delayPath();
        if (Files.notExists(p)) {
            DELAY_INSTANCE = new DelayConfig();
            save();
            return;
        }
        try {
            String json = Files.readString(p);
            DelayConfig cfg = GSON.fromJson(json, DelayConfig.class);
            if (cfg == null) cfg = new DelayConfig();
            DELAY_INSTANCE = cfg;
        } catch (Exception e) {
            // fallback to safe defaults
            DELAY_INSTANCE = new DelayConfig();
            save();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(path().getParent());
            Files.writeString(path(), GSON.toJson(get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.createDirectories(delayPath().getParent());
            Files.writeString(delayPath(), GSON.toJson(getDelay()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
