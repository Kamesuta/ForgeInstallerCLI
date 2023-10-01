# ForgeInstallerCLI

Allow Forge Installer to install Minecraft 1.13+ with Forge without GUI.  
Also, progress percentage can be outputted to parse.  

You can embed Forge Installer in your launcher!  

This project is based on [ForgeWrapper](https://github.com/ZekerZhayard/ForgeWrapper), simplified (remove MultiMC support) and added progress output.

## How to use

1. Download Forge installer for Minecraft 1.13+ [here](https://files.minecraftforge.net/).
2. Download ForgeInstallerCLI jar file at the [release](https://github.com/Kamesuta/ForgeInstallerCLI/releases) page.
3. Create launcher_profile.json file in install location if not exists. (Write `{}` in the file.)
4. Run the below command in terminal:

## Command
```
java -jar <ForgeInstallerCLI.jar> --installer <forge-installer.jar> --target <install-location> [--progress]
```

### Required arguments
- `--installer`: Forge Installer jar file  
  ex) forge-1.16.5-36.2.2-installer.jar  
- `--target`: Minecraft Install Location  
    ex) %appdata%\.minecraft  

### Optional arguments
- `--progress`: Show progress percentage to output.  
  default) disabled  
  ```
  [Forge Installer] [Progress.Start] Building Processors
  [Forge Installer] [Progress] 16%
  [Forge Installer] ===============================================================================
  [Forge Installer]   MainClass: net.minecraftforge.installertools.ConsoleTool
  ```
  - `[Forge Installer] ` is outputted every output to allow auto parser like launcher to recognize each chunk of lines.
  - `[Progress]` is outputted when [progress event](src/main/java/com/kamesuta/forgeinstallercli/ConsoleProgressCallback.java) is fired.
  - `[Progress.Start]`/`[Progress.Stage]` is outputted when [start and stage event](src/main/java/com/kamesuta/forgeinstallercli/ConsoleProgressCallback.java) is fired.

## Build

1. Clone this repository.
2. Load this project in IntelliJ IDEA.
3. Set Java SDK to Java 8. (Java 9+ is not supported.)
4. Build this project.
