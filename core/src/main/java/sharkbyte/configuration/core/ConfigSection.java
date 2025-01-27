package sharkbyte.configuration.core;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public abstract class ConfigSection {

    protected ConfigSection parent;

    protected ConfigSection(ConfigSection parent) {
        this.parent = parent;
    }

    public ConfigSection() {
        this.parent = null;
    }

    /*
     * Locate the node.
     */

    public abstract Set<ConfigSection> getChildren();

    public abstract String getKey();

    public ConfigSection getParent() {
        return parent;
    }

    public ConfigSection getRoot() {
        ConfigSection section = this;
        while (section.getParent() != null) section = section.getParent();
        return section;
    }

    /*
     * Get data.
     */

    public abstract ConfigSection getConfigSection(Object... keys);

    public abstract <E extends Serializable> List<E> getList(Class<E> classType, String node);

    public abstract <E extends Serializable> E getObject(Class<E> classType, String node, E defaultValue);

    public abstract boolean hasNode(Object node);

    /*
     * Set data.
     */

    public abstract <E extends Serializable> void setList(Class<E> classType, String node, List<E> value);

    public abstract <E extends Serializable> void setObject(Class<E> classType, String node, E object);

    /*
     * Node modification.
     */

    public abstract void addNode(Object key);

    public abstract void removeNode(Object key);
}
