package de.pasuki.colorful_redstone_lamps.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;

public class InvertedRedstoneLampBlock extends RedstoneLampBlock {
    public static final BooleanProperty LIT = RedstoneLampBlock.LIT;
    private static final int SHUTDOWN_DELAY_TICKS = 4;

    public InvertedRedstoneLampBlock(Properties props) {
        super(props);
        // Default state is lit: inverted lamps are on without redstone signal.
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, true));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean hasSignal = ctx.getLevel().hasNeighborSignal(ctx.getClickedPos());
        // Inverted behavior: powered -> off, unpowered -> on.
        return this.defaultBlockState().setValue(LIT, !hasSignal);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        if (level.isClientSide()) {
            return;
        }

        boolean lit = state.getValue(LIT);
        boolean hasSignal = level.hasNeighborSignal(pos);

        // No update needed if state already matches inverted behavior.
        if (lit != hasSignal) {
            return;
        }

        if (lit) {
            // If currently lit and receiving power, schedule delayed shutdown.
            level.scheduleTick(pos, this, SHUTDOWN_DELAY_TICKS);
        } else {
            // If currently unlit and power was removed, turn on immediately.
            level.setBlock(pos, state.setValue(LIT, true), Block.UPDATE_CLIENTS);
        }
    }

    // Called by the scheduled tick from neighborChanged for delayed inverted shutdown.
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Turn off only if still powered.
        if (state.getValue(LIT) && level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_CLIENTS);
        }
    }
}