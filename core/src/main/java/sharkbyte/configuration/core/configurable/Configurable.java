package sharkbyte.configuration.core.configurable;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import sharkbyte.configuration.core.ConfigurationFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a way of interacting with configuration files. When they're loaded, each associated configurable
 * will be run and given an opportunity to grab values from the configuration.
 *
 * @Author: am noah
 * @Since: 1.0.0
 * @Updated: 1.0.1
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
     * These may not be suited to every use case, so don't be scared to manually access nodes using the
     * CommentedConfigurationNode located at configuration.getNode(). If you modify the CommentedConfigurationNode,
     * remember to run configuration.setModified(true) or else it won't save.
     */

    /**
     * Return the boolean located at a given node.
     */
    public boolean getBoolean(String... nodes) {
        return getNode(nodes).getBoolean();
    }

    /**
     * Return the boolean located at the given node if present, otherwise returning a default boolean.
     */
    public boolean getBooleanOrDefault(boolean defaultBool, String... nodes) {
        if (hasNode(nodes)) return getBoolean(nodes);
        setObject(Boolean.class, defaultBool, nodes);
        return defaultBool;
    }

    /**
     * Return the double located at a given node.
     */
    public double getDouble(String... nodes) {
        return getNode(nodes).getDouble();
    }

    /**
     * Return the double located at the given node if present, otherwise returning a default double.
     */
    public double getDoubleOrDefault(double defaultDouble, String... nodes) {
        if (hasNode(nodes)) return getDouble(nodes);
        setObject(Double.class, defaultDouble, nodes);
        return defaultDouble;
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
     * Return the integer located at the given node if present, otherwise returning a default integer.
     */
    public int getIntOrDefault(int defaultInt, String... nodes) {
        if (hasNode(nodes)) return getInt(nodes);
        setObject(Integer.class, defaultInt, nodes);
        return defaultInt;
    }

    /**
     * Return the List located at a given node.
     */
    public <V extends Serializable> List<V> getList(Class<V> vClass, String... nodes) {
        try {
            return getNode(nodes).getList(vClass);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Return the List located at the given node if present, otherwise returning a default List.
     */
    public <V extends Serializable> List<V> getListOrDefault(Class<V> vClass, List<V> vList, String... nodes) {
        if (hasNode(nodes)) return getList(vClass, nodes);
        setList(vClass, vList, nodes);
        return vList;
    }

    /**
     * Return the long located at a given node.
     */
    public long getLong(String... nodes) {
        return getNode(nodes).getLong();
    }

    /**
     * Return the long located at the given node if present, otherwise returning a default long.
     */
    public long getLongOrDefault(long defaultLong, String... nodes) {
        if (hasNode(nodes)) return getLong(nodes);
        setObject(Long.class, defaultLong, nodes);
        return defaultLong;
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
     * Return the String located at the given node if present, otherwise returning a default String.
     */
    public String getStringOrDefault(String defaultString, String... nodes) {
        if (hasNode(nodes)) return getString(nodes);
        setObject(String.class, defaultString, nodes);
        return defaultString;
    }

    /**
     * Return the object located at a given node.
     */
    public <V extends Serializable> V getObject(Class<V> vClass, String... nodes) {
        try {
            return getNode(nodes).get(vClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the object located at the given node if present, otherwise returning a default object.
     */
    public <V extends Serializable> V getObjectOrDefault(Class<V> vClass, V defaultV, String... nodes) {
        if (hasNode(nodes)) return getObject(vClass, nodes);
        setObject(vClass, defaultV, nodes);
        return defaultV;
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
     * Returns whether it was able to successfully set the object.
     */
    public <V extends Serializable> boolean setList(Class<V> vClass, List<V> list, String... nodes) {
        try {
            getNode(nodes).set(vClass, list);
            configuration.setModified(true);
        } catch (SerializationException exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Set the value of a given node.
     * Returns whether it was able to successfully set the object.
     */
    public <V extends Serializable> boolean setObject(Class<V> vClass, V obj, String... nodes) {
        try {
            getNode(nodes).set(vClass, obj);
            configuration.setModified(true);
        } catch (SerializationException exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }
}
