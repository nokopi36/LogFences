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
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class LogFenceGateBlock extends FenceGateBlock {
    private final boolean flammable;

    public LogFenceGateBlock(WoodType type, Properties properties, boolean flammable) {
        super(type, properties);
        this.flammable = flammable;
        this.registerDefaultState(BarkStripping.withAllBark(this.defaultBlockState(), GatePart.values()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        for (GatePart part : GatePart.values()) {
            builder.add(part.strippedProperty());
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hitResult) {
        // 開いた扉は当たり判定の外にあるので、剥ぐのは閉じているときだけ。それ以外は通常の開閉
        if (!BarkStripping.isAxe(stack) || state.getValue(OPEN)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        Vec3 local = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        // ゲートは向きに対して左右対称なので、長さ方向の座標だけ取り出せばよい
        double along = state.getValue(FACING).getAxis() == Direction.Axis.Z ? local.x : local.z;
        GatePart part = GatePart.fromHit(along, local.y, state.getValue(IN_WALL));
        if (BarkStripping.isStripped(state, part)) {
            // 剥がれ済みの部位なら開閉する
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        BarkStripping.strip(state, part, level, pos, player, stack, hand);
        return InteractionResult.SUCCESS;
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
