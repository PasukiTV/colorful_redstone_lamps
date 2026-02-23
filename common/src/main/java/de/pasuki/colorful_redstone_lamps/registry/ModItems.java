package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.EnumMap;
import java.util.Map;

public final class ModItems {
    private ModItems() {}

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ColorfulRedstoneLamps.MOD_ID, Registries.ITEM);

    public static final Map<DyeColor, RegistrySupplier<Item>> LAMP_ITEMS =
            new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, RegistrySupplier<Item>> INVERTED_LAMP_ITEMS =
            new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            String normalName = color.getName() + "_redstone_lamp";
            LAMP_ITEMS.put(color, ITEMS.register(normalName, () ->
                    new BlockItem(ModBlocks.LAMPS.get(color).get(), new Item.Properties())
            ));

            String invertedName = color.getName() + "_redstone_lamp_inverted";
            INVERTED_LAMP_ITEMS.put(color, ITEMS.register(invertedName, () ->
                    new BlockItem(ModBlocks.INVERTED_LAMPS.get(color).get(), new Item.Properties())
            ));
        }
    }

    public static void register() {
        ITEMS.register();
    }
}