package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import de.pasuki.colorful_redstone_lamps.block.InvertedRedstoneLampBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.EnumMap;
import java.util.Map;

public final class ModBlocks {
    private ModBlocks() {}

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ColorfulRedstoneLamps.MOD_ID, Registries.BLOCK);

    public static final Map<DyeColor, RegistrySupplier<Block>> LAMPS = new EnumMap<>(DyeColor.class);
    public static final Map<DyeColor, RegistrySupplier<Block>> INVERTED_LAMPS = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {

            // ===== Normal Lamp =====
            final String name = color.getSerializedName() + "_redstone_lamp";

            RegistrySupplier<Block> lamp = BLOCKS.register(name, () ->
                    new RedstoneLampBlock(BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(
                                    Registries.BLOCK,
                                    Identifier.fromNamespaceAndPath(
                                            ColorfulRedstoneLamps.MOD_ID,
                                            name
                                    )
                            ))
                            .mapColor(color.getMapColor())
                            .strength(0.3F)
                            .sound(SoundType.GLASS)
                            .lightLevel(state ->
                                    state.getValue(RedstoneLampBlock.LIT) ? 15 : 0
                            )
                    )
            );

            LAMPS.put(color, lamp);
            registerBlockItem(name, lamp);

            // ===== Inverted Lamp =====
            final String invertedName = color.getSerializedName() + "_redstone_lamp_inverted";

            RegistrySupplier<Block> invertedLamp = BLOCKS.register(invertedName, () ->
                    new InvertedRedstoneLampBlock(BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(
                                    Registries.BLOCK,
                                    Identifier.fromNamespaceAndPath(
                                            ColorfulRedstoneLamps.MOD_ID,
                                            invertedName
                                    )
                            ))
                            .mapColor(color.getMapColor())
                            .strength(0.3F)
                            .sound(SoundType.GLASS)
                            .lightLevel(state ->
                                    state.getValue(BlockStateProperties.LIT) ? 15 : 0
                            )
                    )
            );

            INVERTED_LAMPS.put(color, invertedLamp);
            registerBlockItem(invertedName, invertedLamp);
        }
    }

    private static void registerBlockItem(String name, RegistrySupplier<Block> block) {
        ModItems.ITEMS.register(name, () ->
                new BlockItem(
                        block.get(),
                        new BlockItem.Properties().setId(
                                ResourceKey.create(
                                        Registries.ITEM,
                                        Identifier.fromNamespaceAndPath(ColorfulRedstoneLamps.MOD_ID, name)
                                )
                        )
                )
        );
    }

    public static void register() {
        BLOCKS.register();
    }
}