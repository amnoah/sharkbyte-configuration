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
 * @Updated: 1.0.1
 */
public class ConfigurationExample extends JavaPlugin {

    @Override
    public void onEnable() {
        Path directory = getDataFolder().toPath();

        /*
         * The following code will:
         * - Create a new configuration file called "config" with an associated file in the resources bundle.
         * - Create and add a RunnableConfigurable called "newConfigurable" to that config file with the following task:
         *   > Print the message "Runnable Configurable Ran!" to console.
         * - Create and add the ConfigConfigurable to that config file.
         * - Load "config".
         */

        // Create our configuration file.
        ConfigurationFile config = new ConfigurationFile("config", directory, "1.0.0", ConfigurationExample.class.getResourceAsStream("/config.yml"));

        // Register configurables to the configuration file.
        config.registerConfigurable(new RunnableConfigurable(() -> getLogger().info("Runnable Configurable Ran!")));
        config.registerConfigurable(new ConfigConfigurable(this));

        // Finally, load the configuration file.
        try {
            config.load();
        } catch (Exception e) {
            getLogger().warning("Could not load configuration!");
        }

        /*
         * The following code will:
         * - Create a new configuration file called "newconfig" with no associated file in the resources bundle.
         * - Create and add a RunnableConfigurable called "newConfigurable" to that config file with the following task:
         *   > Write the long 125 to the node "saved" -> "long"
         *   > Check if the node "saved" -> "long" is written.
         *   > Check if the node "saved" -> "int" is written.
         *   > Check if the node "saved" is written.
         *   > Delete the node "saved".
         * - Load "newconfig".
         */

        // Create our configuration file and configurables.
        ConfigurationFile newConfig = new ConfigurationFile("newconfig", directory, "1.0.0");
        RunnableConfigurable newConfigurable = new RunnableConfigurable();
        newConfig.registerConfigurable(newConfigurable);

        // Set the value of our runnable configurable.
        newConfigurable.setRunnable(() -> {
            newConfigurable.setObject(Long.class, 125L, "saved", "long");

            getLogger().info("Has saved -> long? " + newConfigurable.hasNode("saved", "long"));
            getLogger().info("Has saved -> int? " + newConfigurable.hasNode("saved", "int"));
            getLogger().info("Has saved? " + newConfigurable.hasNode("saved"));
            getLogger().info("Removing saved! " + newConfigurable.removeNode("saved"));
        });

        // Finally, load the configuration file.
        try {
            newConfig.load();
        } catch (Exception e) {
            getLogger().warning("Could not load configuration!");
        }
    }
}
