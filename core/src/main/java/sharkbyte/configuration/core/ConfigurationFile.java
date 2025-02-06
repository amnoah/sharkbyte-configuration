package sharkbyte.configuration.core;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public abstract class ConfigurationFile {

    protected final String fileName;
    protected final Path directoryPath, filePath;
    protected final InputStream input;

    protected File configFile;

    public ConfigurationFile(String fileName, Path directoryPath) {
        this.fileName = fileName;
        this.directoryPath = directoryPath;
        this.input = null;
        this.filePath = directoryPath.resolve(fileName);
    }

    public ConfigurationFile(String fileName, Path directoryPath, InputStream input) {
        this.fileName = fileName;
        this.directoryPath = directoryPath;
        this.input = input;
        this.filePath = directoryPath.resolve(fileName);
    }

    public ConfigSection load() {
        try {
            if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);
            File configFile = filePath.toFile();

            if (!Files.exists(filePath)) {
                if (input != null) Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
                else configFile.createNewFile();
            }

            this.configFile = configFile;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public abstract void save();
}
