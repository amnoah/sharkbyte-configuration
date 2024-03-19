package sharkbyte.configuration.example;

import sharkbyte.configuration.core.configurable.Configurable;

import java.util.List;

/**
 * This class serves as an example of how a Configurable object could be implemented.
 *
 * @Author: am noah
 * @Since: 1.0.0
 * @Updated: 1.0.0
 */
public class ConfigConfigurable extends Configurable {

    private final ConfigurationExample plugin;

    /**
     * Initialize the Configurable object.
     */
    public ConfigConfigurable(ConfigurationExample plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handleLoad() {
        plugin.getLogger().info("entry -> sub-entry -> integer: " + getInt("entry", "sub-entry", "integer"));
        plugin.getLogger().info("entry -> double: " + getDouble("entry", "double"));

        try {
            List<String> words = getList(String.class, "list");
            for (String s : words) {
                plugin.getLogger().info("list: " + s);
            }
        } catch (Exception e) {
            plugin.getLogger().info("Could not process list!");
        }

        try {
            setObject(Integer.class, 125, "entry", "sub-entry", "integer");
        } catch (Exception e) {
            plugin.getLogger().info("Could not set integer!");
        }
    }
}
