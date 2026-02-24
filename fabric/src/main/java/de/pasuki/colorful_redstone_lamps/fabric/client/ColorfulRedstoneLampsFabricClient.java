package de.pasuki.colorful_redstone_lamps.fabric.client;

import net.fabricmc.api.ClientModInitializer;

public final class ColorfulRedstoneLampsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TooltipHandler.register();
    }
}
