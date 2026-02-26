package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    private ModTags() {}

    public static final class Items {
        public static final TagKey<Item> ANY_LAMP = tag("any_lamp");
        public static final TagKey<Item> LAMPS = tag("redstone_lamps");
        public static final TagKey<Item> INVERTED_LAMPS = tag("inverted_redstone_lamps");

        private static TagKey<Item> tag(String path) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ColorfulRedstoneLamps.MOD_ID, path));
        }
    }

    public static final class Blocks {
        public static final TagKey<Block> ANY_LAMP = tag("any_lamp");
        public static final TagKey<Block> LAMPS = tag("redstone_lamps");
        public static final TagKey<Block> INVERTED_LAMPS = tag("inverted_redstone_lamps");

        private static TagKey<Block> tag(String path) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ColorfulRedstoneLamps.MOD_ID, path));
        }
    }
}