package sharkbyte.configuration.spigot;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import sharkbyte.configuration.core.ConfigSection;
import sharkbyte.configuration.core.ConfigurationFile;

import java.io.InputStream;

public class SpigotConfigurationFile extends ConfigurationFile {

    private YamlConfiguration rootConf;

    public SpigotConfigurationFile(JavaPlugin plugin, String fileName) {
        super(fileName, plugin.getDataFolder().toPath());
    }

    public SpigotConfigurationFile(JavaPlugin plugin, String fileName, InputStream input) {
        super(fileName, plugin.getDataFolder().toPath(), input);
    }

    @Override
    public ConfigSection load() {
        super.load();
        rootConf = YamlConfiguration.loadConfiguration(configFile);
        return new SpigotConfigSection(rootConf);
    }

    @Override
    public void save() {
        try {
            rootConf.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
