package com.kamesuta.forgeinstallercli.impl;

import net.minecraftforge.installer.actions.ActionCanceledException;
import net.minecraftforge.installer.actions.ClientInstall;
import net.minecraftforge.installer.actions.ProgressCallback;
import net.minecraftforge.installer.json.Install;
import net.minecraftforge.installer.json.InstallV1;
import net.minecraftforge.installer.json.Util;

import java.io.File;
import java.util.function.Predicate;

/**
 * Installer for Forge V1 installer.
 */
public class V1ClientInstall extends ClientInstall {
    public V1ClientInstall(Install profile, ProgressCallback monitor) {
        // Upgrade InstallV0 instance to InstallV1 instance if needed.
        super(profile instanceof InstallV1 ? (InstallV1) profile : new InstallV1(profile), monitor);
    }

    public static Install loadInstallProfile() {
        return Util.loadInstallProfile();
    }

    @Override
    public boolean run(File target, Predicate<String> optionals, File installer) throws ActionCanceledException {
        return super.run(target, optionals, installer);
    }
}
