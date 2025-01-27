package sharkbyte.configuration.configurate;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import sharkbyte.configuration.core.ConfigSection;
import sharkbyte.configuration.core.ConfigurationFile;

import java.io.InputStream;
import java.nio.file.Path;

public class ConfigurateConfigationFile extends ConfigurationFile {

    private YamlConfigurationLoader loader;
    private CommentedConfigurationNode rootNode;

    public ConfigurateConfigationFile(String fileName, Path directoryPath, InputStream input) {
        super(fileName, directoryPath, input);
    }

    public ConfigurateConfigationFile(String fileName, Path directoryPath) {
        super(fileName, directoryPath);
    }

    @Override
    public ConfigSection load() {
        super.load();
        loader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).path(filePath).build();

        try {
            rootNode = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ConfigurateConfigSection(rootNode);
    }

    @Override
    public void save() {
        try {
            loader.save(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
