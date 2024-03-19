package sharkbyte.configuration.core.configurable;

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

    private Runnable runnable;

    /**
     * Initialize the RunnableConfigurable object.
     */
    public RunnableConfigurable() {
        runnable = null;
    }

    /**
     * Initialize the RunnableConfigurable object.
     */
    public RunnableConfigurable(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Set the associated runnable.
     */
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Run the associated runnable when a load is performed on the associated configuration file.
     */
    @Override
    public void handleLoad() {
        if (runnable != null) runnable.run();
    }
}
