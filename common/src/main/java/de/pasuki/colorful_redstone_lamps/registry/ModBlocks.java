package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import de.pasuki.colorful_redstone_lamps.block.InvertedRedstoneLampBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public final class ModBlocks {
    private ModBlocks() {}

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ColorfulRedstoneLamps.MOD_ID, Registries.BLOCK);

    // Helper methods used by BlockBehavior.Properties.
    private static ToIntFunction<BlockState> litBlockEmission() {
        return state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0;
    }

    private static boolean always(BlockState s, BlockGetter g, BlockPos p, EntityType<?> t) {
        return true;
    }

    private static BlockBehaviour.Properties baseProps(DyeColor color) {
        return BlockBehaviour.Properties.of()
                .mapColor(color.getMapColor())
                .strength(0.3F)
                .sound(SoundType.GLASS)
                .lightLevel(litBlockEmission())
                .isValidSpawn(ModBlocks::always);
    }

    // Lamp registries for all dye colors (normal and inverted variants).
    public static final Map<DyeColor, RegistrySupplier<Block>> LAMPS =
            new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, RegistrySupplier<Block>> INVERTED_LAMPS =
            new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            // Normal lamp variant
            String baseName = color.getName() + "_redstone_lamp";
            RegistrySupplier<Block> lamp = registerBlock(baseName,
                    () -> new RedstoneLampBlock(baseProps(color)));
            LAMPS.put(color, lamp);

            // Inverted lamp variant
            String invName = color.getName() + "_redstone_lamp_inverted";
            RegistrySupplier<Block> invLamp = registerBlock(invName,
                    () -> new InvertedRedstoneLampBlock(baseProps(color)));
            INVERTED_LAMPS.put(color, invLamp);
        }
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register() {
        BLOCKS.register();
    }
}