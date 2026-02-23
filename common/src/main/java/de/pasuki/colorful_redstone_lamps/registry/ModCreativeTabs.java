package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeTabs {
    private ModCreativeTabs() {}

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(ColorfulRedstoneLamps.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> NORMAL_LAMPS =
            TABS.register("normal_lamps", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP,1)
                    // Später ersetzen wir das Icon durch eine deiner Lampen
                    .icon(() -> new ItemStack(ModItems.LAMP_ITEMS.get(DyeColor.RED)))
                    // Titel ist erstmal hardcoded, später machen wir lang keys
                    .title(Component.literal("Colorful Redstone Lamps"))
                    // Erstmal nur Vanilla anzeigen, bis deine Items drin sind
                    .displayItems((params,output) -> {
                        for (DyeColor color : DyeColor.values()) {
                            var sup = ModBlocks.LAMPS.get(color);
                            if (sup != null) output.accept(sup.get());
                        }
                    })
                    .build()
            );

    public static final RegistrySupplier<CreativeModeTab> INVERTED_LAMPS =
            TABS.register("inverted_lamps", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP,2)
                    // Später ersetzen wir das Icon durch eine deiner Lampen
                    .icon(() -> new ItemStack(ModItems.INVERTED_LAMP_ITEMS.get(DyeColor.RED)))
                    // Titel ist erstmal hardcoded, später machen wir lang keys
                    .title(Component.literal("Inverted Colorful Redstone Lamps"))
                    // Erstmal nur Vanilla anzeigen, bis deine Items drin sind
                    .displayItems((params,output) -> {
                        for (DyeColor color : DyeColor.values()) {
                            var sup = ModBlocks.INVERTED_LAMPS.get(color);
                            if (sup != null) output.accept(sup.get());
                        }
                    })
                    .build()
            );
    public static void register() {
        TABS.register();
    }
}