package xyz.quazaros.epfragments73.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "epfragments73.json";
    private static ModConfig INSTANCE;

    private static Path path() {
        return FMLPaths.CONFIGDIR.get().resolve(FILE_NAME);
    }

    public static ModConfig get() {
        if (INSTANCE == null) load();
        return INSTANCE;
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
    }

    public static void save() {
        try {
            Files.createDirectories(path().getParent());
            Files.writeString(path(), GSON.toJson(get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
