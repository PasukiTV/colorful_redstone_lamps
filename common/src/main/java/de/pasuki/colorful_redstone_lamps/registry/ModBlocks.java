package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import de.pasuki.colorful_redstone_lamps.block.InvertedRedstoneLampBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static net.minecraft.commands.arguments.ResourceKeyArgument.key;

public final class ModBlocks {
    private ModBlocks() {}

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ColorfulRedstoneLamps.MOD_ID, Registries.BLOCK);

    // Maps: normal & invertiert
    public static final Map<DyeColor,  RegistrySupplier<Block>> LAMPS = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor,  RegistrySupplier<Block>> INVERTED_LAMPS = new EnumMap<>(DyeColor.class);

    // Helper methods used by BlockBehavior.Properties.
    private static ToIntFunction<BlockState> litBlockEmission() {
        return state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0;
    }

    private static boolean always(BlockState s, BlockGetter g, BlockPos p, EntityType<?> t) {
        return true;
    }

    static {
        for (DyeColor color : DyeColor.values()) {
            // ===== normale Lampe =====
            final String name = color.getName() + "_redstone_lamp";
            RegistrySupplier<Block> lamp = BLOCKS.register(name, () ->
                    new RedstoneLampBlock(BlockBehaviour.Properties.of()
                            .setId(key(name))
                            .mapColor(color.getMapColor())
                            .strength(0.3F)
                            .sound(SoundType.GLASS)
                            .lightLevel(s -> s.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
                    )
            );
            LAMPS.put(color, lamp);
            registerBlockItem(name, lamp);

            // ===== invertierte Lampe =====
            final String invName = color.getName() + "_redstone_lamp_inverted";
            RegistrySupplier<Block> invLamp = BLOCKS.register(invName, () ->
                    new InvertedRedstoneLampBlock(BlockBehaviour.Properties.of()
                            .setId(key(invName))
                            .mapColor(color.getMapColor())
                            .strength(0.3F)
                            .sound(SoundType.GLASS)
                            .lightLevel(s -> s.getValue(BlockStateProperties.LIT) ? 15 : 0)
                    )
            );
            INVERTED_LAMPS.put(color, invLamp);
            registerBlockItem(invName, invLamp);
        }
    }
    // === Helpers ===

    private static ResourceKey<Block> key(String name) {
        return ResourceKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(ColorfulRedstoneLamps.MOD_ID, name)
        );
    }

    private static void registerBlockItem(String name, RegistrySupplier<Block> block) {
        ModItems.ITEMS.register(name, () ->
                new BlockItem(block.get(),
                        new BlockItem.Properties().setId(
                                ResourceKey.create(
                                        Registries.ITEM,
                                        ResourceLocation.fromNamespaceAndPath(ColorfulRedstoneLamps.MOD_ID, name)
                                )
                        )
                )
        );
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register() {
        BLOCKS.register();
    }
}