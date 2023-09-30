package com.kamesuta.forgeinstallercli;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main class for Forge Installer CLI.
 */
public class Main {
    /**
     * Entry point for Forge Installer CLI.
     * @param args Command line arguments
     * @throws Throwable If an error occurs
     */
    public static void main(String[] args) throws Throwable {
        // Parse arguments
        List<String> argsList = Stream.of(args).collect(Collectors.toList());
        if (argsList.size() < 4) {
            System.out.println("Invalid arguments! Use: java -jar <ForgeCLI.jar> --installer <forge-installer.jar> --target <install-location>");
            System.exit(1);
            return;
        }
        Path installerJar = Paths.get(argsList.get(argsList.indexOf("--installer") + 1)).toAbsolutePath();
        Path targetDir = Paths.get(argsList.get(argsList.indexOf("--target") + 1)).toAbsolutePath();

        // Check Forge installer jar exists
        if (!Files.exists(installerJar)) {
            System.out.println("Forge Installer is not found!");
            System.exit(1);
            return;
        }

        // Create class loader which loads Forge installer jar
        try (URLClassLoader ucl = URLClassLoader.newInstance(new URL[]{
                Main.class.getProtectionDomain().getCodeSource().getLocation(),
                installerJar.toUri().toURL()
        }, getParentClassLoader())) {
            // Run Forge installer from class loader
            Class<?> installer = ucl.loadClass("com.kamesuta.forgeinstallercli.Installer");
            installer.getMethod("install", File.class, File.class).invoke(null, targetDir.toFile(), installerJar.toFile());
        }
    }

    /**
     * Get parent class loader.
     * @see <a href="https://github.com/MinecraftForge/Installer/blob/fe18a16/src/main/java/net/minecraftforge/installer/actions/PostProcessors.java#L287-L303">PostProcessors.java</a>
     * @return Parent class loader
     */
    private static ClassLoader getParentClassLoader() {
        if (!System.getProperty("java.version").startsWith("1.")) {
            try {
                return (ClassLoader) ClassLoader.class.getDeclaredMethod("getPlatformClassLoader").invoke(null);
            } catch (Exception e) {
                System.out.println("No platform classloader: " + System.getProperty("java.version"));
            }
        }
        return null;
    }
}
