package com.nokopi.logfences.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class LogFenceBlock extends FenceBlock {
    private final boolean flammable;

    public LogFenceBlock(Properties properties, boolean flammable) {
        super(properties);
        this.flammable = flammable;
        this.registerDefaultState(BarkStripping.withAllBark(this.defaultBlockState(), FencePart.values()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        for (FencePart part : FencePart.values()) {
            builder.add(part.strippedProperty());
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hitResult) {
        if (!BarkStripping.isAxe(stack)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        Vec3 local = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        FencePart part = FencePart.fromHit(local.x, local.y, local.z, hasRails(state));
        if (BarkStripping.isStripped(state, part)) {
            // 剥がれ済みなら何もせず、通常の右クリック（リードを結ぶなど）に回す
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        BarkStripping.strip(state, part, level, pos, player, stack, hand);
        return InteractionResult.SUCCESS;
    }

    private static boolean hasRails(BlockState state) {
        return state.getValue(NORTH) || state.getValue(EAST) || state.getValue(SOUTH) || state.getValue(WEST);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.flammable ? LogFenceProperties.FLAMMABILITY : 0;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.flammable ? LogFenceProperties.FIRE_SPREAD_SPEED : 0;
    }
}
