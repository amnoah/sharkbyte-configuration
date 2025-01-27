package sharkbyte.configuration.configurate;

import org.spongepowered.configurate.CommentedConfigurationNode;
import sharkbyte.configuration.core.ConfigSection;

import java.io.Serializable;
import java.util.*;

public class ConfigurateConfigSection extends ConfigSection {

    private final CommentedConfigurationNode config;

    private ConfigurateConfigSection(ConfigSection parent, CommentedConfigurationNode config) {
        super(parent);
        this.config = config;
    }

    public ConfigurateConfigSection(CommentedConfigurationNode config) {
        this.config = config;
    }

    @Override
    public Set<ConfigSection> getChildren() {
        Set<ConfigSection> nodes = new HashSet<>();
        for (CommentedConfigurationNode node : config.childrenMap().values()) nodes.add(new ConfigurateConfigSection(this, node));
        return nodes;
    }

    @Override
    public String getKey() {
        return config.key().toString();
    }

    @Override
    public ConfigSection getConfigSection(Object... keys) {
        ConfigurateConfigSection section = this;
        for (Object key : keys) {
            if (!section.hasNode(key)) return null;
            section = new ConfigurateConfigSection(section, section.config.node(key));
        }
        return section;
    }

    @Override
    public <E extends Serializable> List<E> getList(Class<E> classType, String node) {
        try {
            return config.node(node).getList(classType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public <E extends Serializable> E getObject(Class<E> classType, String node, E defaultValue) {
        try {
            E obj = config.node(node).get(classType);
            return obj == null ? defaultValue : obj;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    @Override
    public boolean hasNode(Object node) {
        return config.node(node).raw() != null;
    }

    @Override
    public <E extends Serializable> void setList(Class<E> classType, String node, List<E> value) {
        try {
            config.node(node).setList(classType, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <E extends Serializable> void setObject(Class<E> classType, String node, E object) {
        try {
            config.node(node).set(classType, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNode(Object key) {
        config.node(key);
    }

    @Override
    public void removeNode(Object key) {
        config.removeChild(key);
    }
}
