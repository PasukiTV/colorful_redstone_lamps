package de.pasuki.colorful_redstone_lamps.registry;

import de.pasuki.colorful_redstone_lamps.ColorfulRedstoneLamps;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneLampBlock;

public final class ModBlocks {
    private ModBlocks() {}

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ColorfulRedstoneLamps.MOD_ID, Registries.BLOCK);

    // Testblock, damit wir sehen, dass Registry bei beiden Loadern funktioniert
    public static final RegistrySupplier<Block> EXAMPLE_BLOCK =
            BLOCKS.register("example_block", () -> new RedstoneLampBlock(Block.Properties.ofFullCopy(Blocks.REDSTONE_LAMP)));

    public static void register() {
        BLOCKS.register();
    }
}