package sharkbyte.configuration.example;

import org.bukkit.plugin.java.JavaPlugin;
import sharkbyte.configuration.core.ConfigurationFile;
import sharkbyte.configuration.core.configurable.RunnableConfigurable;

import java.nio.file.Path;

/**
 * This class serves as an example of how the sharkbyte-configuration system could be used.
 *
 * @Author: am noah
 * @Since: 1.0.0
 * @Updated: 1.0.0
 */
public class ConfigurationExample extends JavaPlugin {

    @Override
    public void onEnable() {
        Path directory = getDataFolder().toPath();

        // Create our configuration file.
        ConfigurationFile config = new ConfigurationFile("config", directory, "1.0.0", ConfigurationExample.class.getResourceAsStream("/config.yml"));

        // Register configurables to the configuration file.
        config.registerConfigurable(new RunnableConfigurable(config, () -> getLogger().info("Runnable Configurable Ran!")));
        config.registerConfigurable(new ConfigConfigurable(config, this));

        // Finally, load the configuration file.
        try {
            config.load();
        } catch (Exception e) {
            getLogger().warning("Could not load configuration!");
        }
    }
}
