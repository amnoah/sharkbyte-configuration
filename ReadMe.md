![Image](/files/SharkByte_Logo.png)

# sharkbyte-configuration

This project is designed to solve configuration issues in platform-independent projects for Minecraft.

When designing a platform-independent project, developers would typically be forced to either include a configuration
reading dependency in their core or create messy solutions to get all configuration options into it without relying on
one solution. With sharkbyte-configuration, you can depend on and use the core module in your platform-independent 
module and then provide an implementation appropriate to your platform.

Recommended:
- Use the `configurate` module for Sponge. Everything is provided by the server.
- Use the `spigot` module for Spigot/Paper/Folia. Everything is provided by the server.
- Use the `snakeyaml` module for all other platforms. You must provide snakeyaml as a dependency.