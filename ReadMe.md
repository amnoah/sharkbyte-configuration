![Image](/files/SharkByte_Logo.png)

# sharkbyte-configuration

This is a platform-independent, object-oriented configuration management system originally developed for the sharkbyte
project. It works as a wrapper for the [Configurate](https://github.com/SpongePowered/Configurate) project, meant to
make development easier. It should be noted that it is intended to be shaded and will NOT handle file name conflicts, 
so it is recommended to keep your files in uniquely named directories.

More information about each module can be found inside their respective folders.

# self promo

If you are using sharkbyte-configuration for Minecraft, I would highly recommend you include
[BetterReload](https://github.com/amnoah/BetterReload) compatibility on the Spigot platform.

BetterReload adds a universal reload event, replacing the traditional /reload command. This event is passed to plugins,
allowing them to handle a reload as they see fit. Your plugin could use this event to cycle through all of your
ConfigurationFile objects and call the load() function.

BetterReload also allows for users to individually reload plugins, removing the need for a reload command to be built
into every plugin.

# how to use

You can add sharkbyte-configuration to your project using [JitPack](https://jitpack.io/#amnoah/sharkbyte-configuration/1.0.0). 
Select the dependency system you're using and copy the repository/dependency settings into your project. From there, 
just reload your dependencies and you should have sharkbyte-configuration accessible from your project.

# support

For general support, please join my [Discord server](https://discord.gg/ey9uTg3hcy).

For issues with the project, please open an issue in the issues tab.
