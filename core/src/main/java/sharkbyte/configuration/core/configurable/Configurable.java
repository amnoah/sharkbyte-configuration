package sharkbyte.configuration.core.configurable;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import sharkbyte.configuration.core.ConfigurationFile;

import java.io.Serializable;
import java.util.List;

/**
 * This class serves as a way of interacting with configuration files. When they're loaded, each associated configurable
 * will be run and given an opportunity to grab values from the configuration.
 *
 * @Author: am noah
 * @Since: 1.0.0
 * @Updated: 1.0.0
 */
public abstract class Configurable {

    private ConfigurationFile configuration;

    /*
     * Getters/Setters.
     */

    /**
     * Return the associated configuration file.
     */
    public ConfigurationFile getConfiguration() {
        return configuration;
    }

    /**
     * Set the associated configuration file.
     */
    public void setConfiguration(ConfigurationFile configuration) {
        this.configuration = configuration;
    }

    /*
     * Abstract methods.
     */

    /**
     * Handle what should happen when the associated configuration file is loaded.
     */
    public abstract void handleLoad();

    /*
     * Node Getters.
     * These may not be suited to every use case, so don't be scared to manually access nodes using the
     * CommentedConfigurationNode located at configuration.getNode(). If you modify the CommentedConfigurationNode,
     * remember to run configuration.setModified(true) or else it won't save.
     */

    /**
     * Return the double located at a given node.
     */
    public double getDouble(String... nodes) {
        return getNode(nodes).getDouble();
    }

    /**
     * Return the float located at a given node.
     */
    public float getFloat(String... nodes) {
        return getNode(nodes).getFloat();
    }

    /**
     * Return the int located at a given node.
     */
    public int getInt(String... nodes) {
        return getNode(nodes).getInt();
    }

    /**
     * Return the List located at a given node.
     */
    public <V> List<V> getList(Class<V> vClass, String... nodes) throws SerializationException {
        return getNode(nodes).getList(vClass);
    }

    /**
     * Return the long located at a given node.
     */
    public long getLong(String... nodes) {
        return getNode(nodes).getLong();
    }

    /**
     * Return the CommentedConfigurationNode located at a given node.
     */
    public CommentedConfigurationNode getNode(String... nodes) {
        CommentedConfigurationNode node = configuration.getNode();
        for (String s : nodes) node = node.node(s);
        return node;
    }

    /**
     * Return the String located at a given node.
     */
    public String getString(String... nodes) {
        return getNode(nodes).getString();
    }

    /**
     * Return the object located at a given node.
     */
    public <V> V getObject(Class<V> vClass, String... nodes) throws SerializationException {
        return getNode(nodes).get(vClass);
    }

    /**
     * Return whether the given node is written in the configuration file.
     */
    public boolean hasNode(String... node) {
        return getNode(node).raw() != null;
    }

    /*
     * Node Modifiers.
     */

    /**
     * Removes the given node from the configuration file.
     * Returns whether it was successful.
     */
    public boolean removeNode(String... nodes) {
        ConfigurationNode node = configuration.getNode();
        for (int i = 0; i < nodes.length - 1; i++) node = node.node(nodes[i]);
        return node.removeChild(nodes[nodes.length - 1]);
    }

    /**
     * Set a List value of the given node.
     */
    public <V extends Serializable> void setList(Class<V> vClass, List<V> list, String... nodes) throws SerializationException {
        getNode(nodes).set(vClass, list);
        configuration.setModified(true);
    }

    /**
     * Set the value of a given node.
     */
    public <V extends Serializable> void setObject(Class<V> vClass, V obj, String... nodes) throws SerializationException {
        getNode(nodes).set(vClass, obj);
        configuration.setModified(true);
    }
}
