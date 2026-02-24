package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.world.level.ItemLike;

public final class ModCreativeTabs {
    private ModCreativeTabs() {}

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(ColorfulRedstoneLamps.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> NORMAL_LAMPS =
            TABS.register("0_normal_lamps", () -> createLampTab(
                    "itemGroup.colorful_redstone_lamps.normal_lamps",
                    () -> new ItemStack(ModItems.LAMP_ITEMS.get(DyeColor.RED).get()),
                    ModBlocks.LAMPS,
                    0
            ));

    public static final RegistrySupplier<CreativeModeTab> INVERTED_LAMPS =
            TABS.register("1_inverted_lamps", () -> createLampTab(
                    "itemGroup.colorful_redstone_lamps.inverted_lamps",
                    () -> new ItemStack(ModItems.INVERTED_LAMP_ITEMS.get(DyeColor.RED).get()),
                    ModBlocks.INVERTED_LAMPS,
                    1
            ));

    private static CreativeModeTab createLampTab(
            String translationKey,
            Supplier<ItemStack> icon,
            Map<DyeColor, ? extends RegistrySupplier<? extends ItemLike>> lampMap,
            int column
    ) {
        return CreativeModeTab.builder(CreativeModeTab.Row.TOP, column)
                .icon(icon)
                .title(Component.translatable(translationKey))
                .displayItems((params, output) -> {
                    for (DyeColor color : DyeColor.values()) {
                        var sup = lampMap.get(color);
                        if (sup != null) {
                            output.accept(sup.get());
                        }
                    }
                })
                .build();
    }

    public static void register() {
        TABS.register();
    }
}