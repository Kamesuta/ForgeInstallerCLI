package com.kamesuta.forgeinstallercli;

import com.kamesuta.forgeinstallercli.impl.V0ClientInstall;
import com.kamesuta.forgeinstallercli.impl.V1ClientInstall;
import net.minecraftforge.installer.actions.ActionCanceledException;
import net.minecraftforge.installer.actions.ProgressCallback;
import net.minecraftforge.installer.json.Install;
import net.minecraftforge.installer.json.InstallV1;
import net.minecraftforge.installer.json.Util;

import java.io.File;

/**
 * Installer for Forge.
 */
public class Installer {
    /**
     * Setup properties and run Forge installer.
     * Called via reflection by {@link Main#main(String[])}.
     *
     * @param target       Install location
     * @param installerJar Forge installer jar
     * @param progress     Show progress percentage in console
     * @return true if success
     */
    @SuppressWarnings("unused")
    public static boolean install(File target, File installerJar, boolean progress) {
        // Create progress monitor
        ProgressCallback monitor = progress
                ? new ConsoleProgressCallback()
                : ProgressCallback.TO_STD_OUT;

        // Setup properties
        // Copied from https://github.com/MinecraftForge/Installer/blob/fe18a16/src/main/java/net/minecraftforge/installer/SimpleInstaller.java#L59-L67
        if (System.getProperty("java.net.preferIPv4Stack") == null) {
            System.setProperty("java.net.preferIPv4Stack", "true");
        }
        String vendor = System.getProperty("java.vendor", "missing vendor");
        String javaVersion = System.getProperty("java.version", "missing java version");
        String jvmVersion = System.getProperty("java.vm.version", "missing jvm version");
        monitor.message(String.format("JVM info: %s - %s - %s", vendor, javaVersion, jvmVersion));
        monitor.message("java.net.preferIPv4Stack=" + System.getProperty("java.net.preferIPv4Stack"));

        // Run Forge installer
        return runClientInstall(monitor, target, installerJar);
    }

    /**
     * Run Forge installer.
     *
     * @param monitor      Progress monitor for progress bar
     * @param target       Install location
     * @param installerJar Forge installer jar
     * @return true if success
     */
    public static boolean runClientInstall(ProgressCallback monitor, File target, File installerJar) {
        // Run Forge installer
        try {
            Class<?> installerClass = Util.class.getMethod("loadInstallProfile").getReturnType();
            if (installerClass.equals(Install.class)) {
                // V0 installer
                Install profile = V0ClientInstall.loadInstallProfile();
                return new V0ClientInstall(profile, monitor).run(target, input -> true);
            } else if (installerClass.equals(InstallV1.class)) {
                // V1 installer
                Install profile = V1ClientInstall.loadInstallProfile();
                return new V1ClientInstall(profile, monitor).run(target, input -> true, installerJar);
            } else {
                throw new IllegalArgumentException("Unable to determine the installer version. (" + installerClass + ")");
            }
        } catch (ReflectiveOperationException | ActionCanceledException e) {
            throw new RuntimeException(e);
        }
    }
}
