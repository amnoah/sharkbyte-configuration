package sharkbyte.configuration.snakeyaml;

import sharkbyte.configuration.core.ConfigSection;

import java.io.Serializable;
import java.util.*;

public class SnakeYAMLConfigSection extends ConfigSection {

    private final String key;
    private final Map<String, Object> config;

    private SnakeYAMLConfigSection(ConfigSection parent, Map<String, Object> config, String key) {
        super(parent);
        this.config = config;
        this.key = key;
    }

    public SnakeYAMLConfigSection(Map<String, Object> config) {
        this.config = config;
        key = "";
    }

    @Override
    public Set<ConfigSection> getChildren() {
        Set<ConfigSection> children = new HashSet<>();

        for (String key : config.keySet()) {
            Object obj = config.get(key);

            if (!(obj instanceof Map)) continue;
            Map<?, ?> map = (Map<?, ?>) obj;
            if (!map.keySet().stream().allMatch(a -> a instanceof String)) continue;
            Map<String, Object> finalMap = (Map<String, Object>) map;

            children.add(new SnakeYAMLConfigSection(this, finalMap, key));
        }

        return children;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public ConfigSection getConfigSection(Object... keys) {
        SnakeYAMLConfigSection section = this;
        for (Object key : keys) {
            if (!hasNode(key)) return null;

            Object obj = section.config.get(key.toString());

            if (!(obj instanceof Map)) continue;
            Map<?, ?> map = (Map<?, ?>) obj;
            if (!map.keySet().stream().allMatch(a -> a instanceof String)) continue;
            Map<String, Object> finalMap = (Map<String, Object>) map;

            section = new SnakeYAMLConfigSection(section, finalMap, key.toString());
        }
        return section;    }

    @Override
    public <E extends Serializable> List<E> getList(Class<E> classType, String node) {
        List<E> obj;

        try {
            obj = (List<E>) config.get(node);
        } catch (Exception e) {
            obj = new ArrayList<>();
        }

        if (obj == null) obj = new ArrayList<>();

        return obj;
    }

    @Override
    public <E extends Serializable> E getObject(Class<E> classType, String node, E defaultValue) {
        E obj;

        try {
            obj = (E) config.get(node);
        } catch (Exception e) {
            obj = defaultValue;
        }

        return obj == null ? defaultValue : obj;
    }

    @Override
    public boolean hasNode(Object node) {
        return config.get(node.toString()) != null;
    }

    @Override
    public <E extends Serializable> void setList(Class<E> classType, String node, List<E> value) {
        config.put(node, value);
    }

    @Override
    public <E extends Serializable> void setObject(Class<E> classType, String node, E object) {
        config.put(node, object);
    }

    @Override
    public void addNode(Object key) {
        Map<String, Object> map = new LinkedHashMap<>();
        config.put(key.toString(), map);
    }

    @Override
    public void removeNode(Object key) {
        config.remove(key.toString());
    }
}
