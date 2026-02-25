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


    public static void register() {
        ITEMS.register();
    }
}