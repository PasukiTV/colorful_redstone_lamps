package de.pasuki.colorful_redstone_lamps.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class InvertedRedstoneLampBlock extends RedstoneLampBlock {
    public static final BooleanProperty LIT = RedstoneLampBlock.LIT;

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
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        boolean lit       = state.getValue(BlockStateProperties.LIT);
        boolean hasSignal = level.hasNeighborSignal(pos);
        // Update only when current state differs from desired inverted state.
        if (lit == hasSignal) {
            if (lit) {
                // if Lamp lit, turn delayed off
                level.scheduleTick(pos, this, 4);
            } else {
                // if Lamp not lit, turn on
                level.setBlock(pos, state.setValue(BlockStateProperties.LIT, Boolean.TRUE), Block.UPDATE_CLIENTS);
            }
        }
    }

    // Called by the scheduled tick from neighborChanged for delayed inverted shutdown.
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Turn Lamp only off, if Lamp is on and has signal
        if (state.getValue(BlockStateProperties.LIT) && level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.setValue(BlockStateProperties.LIT, Boolean.FALSE), Block.UPDATE_CLIENTS);
        }
    }
}