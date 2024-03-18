package sharkbyte.configuration.core.configurable;

import sharkbyte.configuration.core.ConfigurationFile;

/**
 * This is an implementation of the Configurable object that allows for a runnable to called on load.
 * This allows you to create a new instance with a lambda function, not needing to create a new class.
 *
 * Example:
 * new RunnableConfigurable(configuration, () -> {
 *    System.out.println("Configuration file reloaded!");
 * });
 *
 * @Author: am noah
 * @Since: 1.0.0
 * @Updated: 1.0.0
 */
public class RunnableConfigurable extends Configurable {

    private final Runnable runnable;

    /**
     * Initialize the Configurable object.
     */
    public RunnableConfigurable(ConfigurationFile configuration, Runnable runnable) {
        super(configuration);
        this.runnable = runnable;
    }

    /**
     * Run the associated runnable when a load is performed on the associated configuration file.
     */
    @Override
    public void handleLoad() {
        runnable.run();
    }
}
