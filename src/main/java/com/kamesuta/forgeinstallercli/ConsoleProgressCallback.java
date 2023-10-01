package com.kamesuta.forgeinstallercli;

import net.minecraftforge.installer.actions.ProgressCallback;

/**
 * Progress callback for console output.
 */
public class ConsoleProgressCallback implements ProgressCallback {
    private int lastPercent = -1;

    @Override
    public void message(String message, MessagePriority priority) {
        System.out.println("[Forge Installer] " + message);
    }

    @Override
    public void start(String label) {
        message("[Progress.Start] " + label);
    }

    @Override
    public void stage(String message) {
        message("[Progress.Stage] " + message);
    }

    @Override
    public void progress(double progress) {
        int percent = (int) (progress * 100);
        if (percent != lastPercent) {
            lastPercent = percent;
            message("[Progress] " + percent + "%");
        }
    }
}
