package com.kamesuta.forgeinstallercli.impl;

import net.minecraftforge.installer.actions.ActionCanceledException;
import net.minecraftforge.installer.actions.ClientInstall;
import net.minecraftforge.installer.actions.ProgressCallback;
import net.minecraftforge.installer.json.Install;
import net.minecraftforge.installer.json.Util;

import java.io.File;
import java.util.function.Predicate;

/**
 * Installer for Forge V0 installer.
 */
public class V0ClientInstall extends ClientInstall {
    public V0ClientInstall(Install profile, ProgressCallback monitor) {
        super(profile, monitor);
    }

    public static Install loadInstallProfile() {
        return Util.loadInstallProfile();
    }

    @Override
    public boolean run(File target, Predicate<String> optionals) throws ActionCanceledException {
        return super.run(target, optionals);
    }
}
