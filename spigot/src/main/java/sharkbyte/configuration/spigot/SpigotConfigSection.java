package sharkbyte.configuration.spigot;

import org.bukkit.configuration.ConfigurationSection;
import sharkbyte.configuration.core.ConfigurationFile;
import sharkbyte.configuration.core.ConfigSection;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;

public class SpigotConfigSection extends ConfigSection {

    private final ConfigurationSection config;

    private SpigotConfigSection(ConfigSection parent, ConfigurationSection config) {
        super(parent);
        this.config = config;
    }

    public SpigotConfigSection(ConfigurationSection config) {
        this.config = config;
    }

    @Override
    public Set<ConfigSection> getChildren() {
        Set<ConfigSection> children = new HashSet<>();
        for (String key : config.getKeys(false)) children.add(new SpigotConfigSection(this, config.getConfigurationSection(key)));
        return children;
    }

    @Override
    public String getKey() {
        return config.getName();
    }

    @Override
    public @Nullable ConfigSection getConfigSection(Object... keys) {
        SpigotConfigSection section = this;
        ConfigurationSection spigotSection;
        for (Object key : keys) {
            spigotSection = section.config.getConfigurationSection(key.toString());
            if (spigotSection == null) return null;
            section = new SpigotConfigSection(this, spigotSection);
        }
        return section;
    }

    @Override
    public <E extends Serializable> List<E> getList(Class<E> classType, String node) {
        List<E> list = new ArrayList<>();
        for (Object e : config.getList(node, list)) list.add((E) e);
        return list;
    }

    @Override
    public <E extends Serializable> E getObject(Class<E> classType, String node, E defaultValue) {
        E obj = config.getObject(node, classType);
        return obj == null ? defaultValue : obj;    }

    @Override
    public boolean hasNode(Object node) {
        return config.get(node.toString()) != null;
    }

    @Override
    public <E extends Serializable> void setList(Class<E> classType, String node, List<E> value) {
        config.set(node, value);
    }

    @Override
    public <E extends Serializable> void setObject(Class<E> classType, String node, E object) {
        config.set(node, object);
    }

    @Override
    public void addNode(Object key) {
        config.createSection(key.toString());
    }

    @Override
    public void removeNode(Object key) {
        config.set(key.toString(), null);
    }
}