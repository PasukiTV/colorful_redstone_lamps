package de.pasuki.colorful_redstone_lamps;

import de.pasuki.colorful_redstone_lamps.registry.ModBlocks;
import de.pasuki.colorful_redstone_lamps.registry.ModCreativeTabs;
import de.pasuki.colorful_redstone_lamps.registry.ModItems;

public final class ColorfulRedstoneLamps {
    public static final String MOD_ID = "colorful_redstone_lamps";

    public static void init() {
        ModBlocks.register();
        ModItems.register();
        ModCreativeTabs.register();
    }
}
