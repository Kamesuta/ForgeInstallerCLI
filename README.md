# ForgeInstallerCLI

Allow Forge Installer to install Minecraft 1.13+ with Forge without GUI.

## How to use

1. Download Forge installer for Minecraft 1.13+ [here](https://files.minecraftforge.net/).
2. Download ForgeInstallerCLI jar file at the [release](https://github.com/Kamesuta/ForgeInstallerCLI/releases) page.
3. Run the below command in terminal:
   ```
   java -jar <ForgeInstallerCLI.jar> --installer <forge-installer.jar> --target <install-location>
   ```
   3 arguments are required.
   * `--installer`: Forge Installer jar file  
     ex) forge-1.16.5-36.2.2-installer.jar  
   * `--target`: Minecraft Install Location  
     ex) %appdata%\.minecraft  

## Build

1. Clone this repository.
2. Load this project in IntelliJ IDEA.
3. Set Java SDK to Java 8. (Java 9+ is not supported, because legacy (V0) installer is not compatible with Java 9+)
4. Build this project.
