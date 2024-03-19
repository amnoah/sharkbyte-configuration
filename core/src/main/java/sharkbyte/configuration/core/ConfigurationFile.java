package sharkbyte.configuration.core;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import sharkbyte.configuration.core.configurable.Configurable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents and handles basic configuration file needs.
 *
 * @Author: am noah
 * @Since: 1.0.0
 * @Updated: 1.0.0
 */
public class ConfigurationFile {

    private boolean checkVersion = true;

    private final InputStream inputStream;
    private final File file;
    private final Path directoryPath, filePath;
    private final String name, version;

    private final List<Configurable> configurables = new ArrayList<>();

    private CommentedConfigurationNode node;
    private boolean modified;

    /**
     * Initialize the ConfigurationFile object based on a new file.
     *
     * @param name - The file's name (excluding .yml). Example: "config"
     * @param directory - The folder that the file should be created in.
     * @param version - The file's current sb-configuration-core-version (used for sb-configuration-core-version checking).
     */
    public ConfigurationFile(String name, Path directory, String version) {
        this.name = name.toLowerCase();
        this.version = version;
        this.directoryPath = directory;
        this.inputStream = null;
        this.filePath = directoryPath.resolve(this.name + ".yml");
        this.file = filePath.toFile();
    }

    /**
     * Initialize the ConfigurationFile object based on a file in the resources bundle.
     *
     * @param name - The file's name (excluding .yml). Example: "config"
     * @param directory - The folder that the file should be created in.
     * @param version - The file's current sb-configuration-core-version (used for sb-configuration-core-version checking).
     * @param inputStream - An input stream directing to the location of the file in the resources bundle.
     */
    public ConfigurationFile(String name, Path directory, String version, InputStream inputStream) {
        this.name = name.toLowerCase();
        this.version = version;
        this.directoryPath = directory;
        this.inputStream = inputStream;
        this.filePath = directoryPath.resolve(this.name + ".yml");
        this.file = filePath.toFile();
    }

    /*
     * Getters/Setters.
     */

    /**
     * Return the Commented Configuration Node associated with this configuration file.
     * This will only return a non-null value during a reload when the configurable.handleLoad() function is called.
     */
    public CommentedConfigurationNode getNode() {
        return node;
    }

    /**
     * Set whether the config's version should be checked upon loading.
     */
    public void setCheckVersion(boolean checkVersion) {
        this.checkVersion = checkVersion;
    }

    /**
     * Sets the current modified status, determining whether the file will be saved at the end of the reload.
     * This will only be checked after the configurable.handleLoad() function is called and is reset at the beginning
     * of loads.
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /*
     * File Loading/Management.
     */

    /**
     * This method copies the file from the jar to the intended folder location.
     */
    public void createFile() throws IOException {
        // Create all directories and the file itself.
        if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);
        if (!file.exists()) {
            if (inputStream != null) Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            else {
                // Since it's a new file, we have to create it and write the current version.
                file.createNewFile();
                YamlConfigurationLoader configLoader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).path(filePath).build();
                CommentedConfigurationNode node = configLoader.load();
                node.node("v").set(String.class, version);
                configLoader.save(node);
            }
        }
    }

    /**
     * Perform a general load on the configuration file, creating it, checking the version, and calling all Configurable
     * objects' handleLoad() method.
     */
    public void load() throws IOException {
        createFile();

        YamlConfigurationLoader configLoader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).path(filePath).build();
        node = configLoader.load();

        if (checkVersion) {
            // Update the file if out of date, rebuilding the loader and node if updated.
            String currentVersion = node.node("v").getString();
            if (currentVersion == null || !currentVersion.equals(version)) {
                updateFile();
                node = configLoader.load();
            }
        }

        // Pass the reload to configurables, saving the file if one modified the file.
        modified = false;
        for (Configurable configurable : configurables) {
            /*
             * Ensure compatibility for configurables to be added to multiple configuration files.
             * I'm not 100% sure what purpose this may serve as of now, but I'm sure I'll be thankful later.
             */
            configurable.setConfiguration(this);
            configurable.handleLoad();
            configurable.setConfiguration(null);
        }
        if (modified) configLoader.save(node);
        node = null;
    }

    /**
     * This method replaces the currently available configuration file with the one included in the jar, saving a copy
     * of the old file as to not wipe all configurations.
     */
    public void updateFile() throws IOException {
        // Find a name formatted old-(name)-(#).yml that isn't taken.
        int copy = 1;
        while (directoryPath.resolve("old-" + name + "-" + copy + ".yml").toFile().exists()) copy++;

        // Move the current configuration to a file with that name.
        Path destination = directoryPath.resolve("old-" + name + "-" + copy + ".yml");
        Files.copy(filePath, destination, StandardCopyOption.REPLACE_EXISTING);

        // Delete the current configuration and regenerate it.
        file.delete();
        createFile();
    }

    /*
     * Configurable Registering/Unregistering.
     */

    /**
     * Adds a configurable to the configuration file.
     */
    public void registerConfigurable(Configurable configurable) {
        configurables.add(configurable);
    }

    /**
     * Removes a configurable from the configuration file.
     */
    public void unregisterConfigurable(Configurable configurable) {
        configurables.remove(configurable);
    }
}