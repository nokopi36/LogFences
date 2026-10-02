package com.nokopi.logfences.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LogFenceBlock extends FenceBlock {
    // バニラの木の柵と同じ値（FireBlock#bootStrap の setFlammable(OAK_FENCE, 5, 20)）
    private static final int FIRE_SPREAD_SPEED = 5;
    private static final int FLAMMABILITY = 20;

    private final boolean flammable;

    public LogFenceBlock(Properties properties, boolean flammable) {
        super(properties);
        this.flammable = flammable;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.flammable ? FLAMMABILITY : 0;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.flammable ? FIRE_SPREAD_SPEED : 0;
    }
}
