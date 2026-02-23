package de.pasuki.colorful_redstone_lamps.fabric;

import net.fabricmc.api.ModInitializer;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;

public final class ColorfulRedstoneLampsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        ColorfulRedstoneLamps.init();
    }
}
