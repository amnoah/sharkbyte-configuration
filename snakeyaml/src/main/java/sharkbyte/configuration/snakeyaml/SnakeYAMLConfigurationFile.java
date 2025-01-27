package sharkbyte.configuration.snakeyaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import sharkbyte.configuration.core.ConfigSection;
import sharkbyte.configuration.core.ConfigurationFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SnakeYAMLConfigurationFile extends ConfigurationFile {

    private final Yaml yaml;
    private Map<String, Object> root;

    public SnakeYAMLConfigurationFile(String fileName, Path directoryPath) {
        super(fileName, directoryPath);
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndicatorIndent(2);
        options.setIndentWithIndicator(true);
        yaml = new Yaml(options);
    }

    public SnakeYAMLConfigurationFile(String fileName, Path directoryPath, InputStream input) {
        super(fileName, directoryPath, input);
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);      }

    @Override
    public ConfigSection load() {
        super.load();
        root = null;
        try (InputStream inputStream = Files.newInputStream(configFile.toPath())) {
            root = yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (root == null) root = new LinkedHashMap<>();
        return new SnakeYAMLConfigSection(root);
    }

    @Override
    public void save() {
        try(PrintWriter writer = new PrintWriter(configFile)) {
            yaml.dump(root, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
